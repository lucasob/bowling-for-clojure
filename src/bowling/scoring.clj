(ns bowling.scoring)

(defn strike? [[fst _]]
  (= 10 fst))

(defn spare? [[fst snd]]
  "A frame resulted in spare if the first roll was not a strike and if the total is 10."

  (and
    (not (strike? [fst snd]))
    (= (+ fst snd) 10)))

(defn strike-bonus [next-rolls]
  "A strike bonus is the value of the next two *rolls*. If the next roll is a strike then you must go to the next frame."

  (apply + (take 2 (remove nil? next-rolls))))

(defn frame-score [frame rolls]
  "The score for a frame is as follows
    * 10 points for a strike
    * 10 points for a spate
    * The number of pins knocked down otherwise"

  (if
    (strike? frame)
    (+ 10 (strike-bonus rolls))
    (if (spare? frame)
      (+ 10 (first rolls))
      (reduce + frame))))

(defn final-frame [rolls]
  "The score for the final frame is the same, with specific bonuses.
    * A spare: one additional roll
    * A strike: two additional rolls"

  (if (strike? (take 2 rolls))
    (+ 10 (apply + (subvec rolls 2)))
    (if (spare? (take 2 rolls))
      (+ (apply + (take 2 rolls)) (get rolls 2))
      (apply + (take 2 rolls)))))

(defn game-score [rolls]
  "Given a list of rolls, where the length is between 20 and 23. return the score of the game."
  ;TODO this is an eyesore and needs to be broken down accordingly
  (let
    ; the maine game frames are the first 20 rolls
    [game-frames (vec (partition-all 2 (subvec rolls 0 20)))]
    (let
      ; handle the front 9 frames uniformly
      [front-nine (vec (take 9 game-frames))]
      (+
        (reduce + (vec
                    (map-indexed
                      (fn [index frame]
                        (frame-score frame (subvec rolls (* 2 (+ 1 index))))) front-nine)))

        ; The final piece is the score of frame 10 + bonus rolls
        (final-frame (vec (concat (last game-frames) (subvec rolls 20))))))))
