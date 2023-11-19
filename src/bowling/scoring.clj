(ns bowling.scoring)

(defn strike? [[roll]]
  (= 10 roll))

(defn spare? [rolls]
  "A frame resulted in spare if the first roll was not a strike and if the total is 10."
  (and
    (not (strike? rolls))
    (= 10 (reduce + (take 2 rolls)))))

(defn strike-bonus [next-rolls]
  "A strike bonus is the value of the next two *rolls*. If the next roll is a strike then you must go to the next frame."
  (reduce + (take 2 next-rolls)))

(defn strike-score [next-rolls]
  "The score for a strike is 10 + the bonus"
  (+ 10 (strike-bonus next-rolls)))

(defn spare-score [next-rolls]
  "The score for a spare is 10 + the bonus"
  (+ 10 (first next-rolls)))


(defn tenth-frame [rolls]
  "The score for the final frame is the same, with specific bonuses.
    * A spare: one additional roll
    * A strike: two additional rolls"
  (if (strike? rolls)
    (reduce + 10 (take 2 rolls))
    (if (spare? rolls)
      (+ 10 (nth rolls 2))
      (reduce + rolls))))

(defn frame-score-and-rolls [rolls]
  (cond

    (strike? rolls)
    [(strike-score (rest rolls)) (rest rolls)]

    (spare? rolls)
    [(spare-score (drop 2 rolls)) (drop 2 rolls)]

    :default
    [(reduce + (take 2 rolls)) (drop 2 rolls)]))

(defn game-score [rolls]
  "Given a list of rolls, return the score of the game."
  (loop [frame 0
         score 0
         remaining-rolls rolls]
    (if (= frame 9)
      (+ score (tenth-frame remaining-rolls))
      (let [[frame-score rolls] (frame-score-and-rolls remaining-rolls)]
        (recur (inc frame) (+ score frame-score) rolls)))))
