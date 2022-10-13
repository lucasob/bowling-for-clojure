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

(defn strike-score [next-rolls]
  "The score for a strike is 10 + the bonus"
  (+ 10 (strike-bonus next-rolls)))

(defn spare-score [next-rolls]
  "The score for a spare is 10 + the bonus"
  (+ 10 (first next-rolls)))

(defn frame-score [frame next-rolls]
  "The score for a frame is as follows
    * 10 points for a strike
    * 10 points for a spate
    * Otherwise, the number of pins knocked down"
  (if
    (strike? frame)
    (strike-score next-rolls)
    (if (spare? frame)
      (spare-score next-rolls)
      (reduce + frame))))

(defn tenth-frame [rolls]
  "The score for the final frame is the same, with specific bonuses.
    * A spare: one additional roll
    * A strike: two additional rolls"
  (if (strike? (take 2 rolls))
    (+ 10 (apply + (subvec rolls 2)))
    (if (spare? (take 2 rolls))
      (+ (apply + (take 2 rolls)) (get rolls 2))
      (apply + (take 2 rolls)))))

; The main 20 rolls of a game of bowling
(defn main-game [all-rolls] (subvec all-rolls 0 20))

; From a vector of 20 rolls, partition into frames of size 2
(defn as-frames [all-rolls] (partition 2 all-rolls))

; The first nine frames can be scored uniformly
(defn first-nine-frames [frames] (vec (take 9 frames)))

; The bonus rolls are from (20, 22]
(defn bonus-rolls [all-rolls] (subvec all-rolls 20))

(defn game-score [rolls]
  "Given a list of rolls, where the length is between 20 and 23. return the score of the game."
  ;TODO this is an eyesore and needs to be broken down accordingly
  (let
    ; the maine game frames are the first 20 rolls
    [game-frames (vec (as-frames (main-game rolls)))]
    (+
      (reduce
        + (vec
            (map-indexed
              (fn [index frame] (frame-score frame (subvec rolls (* 2 (+ 1 index)))))
              (first-nine-frames game-frames))))

      ; The final piece is the score of frame 10 + bonus rolls
      (tenth-frame (vec (concat (last game-frames) (bonus-rolls rolls)))))))
