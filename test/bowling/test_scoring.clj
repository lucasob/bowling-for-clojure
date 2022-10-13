(ns bowling.test-scoring
  (:require
    [clojure.test :refer :all ]
    [bowling.scoring :refer [strike? spare? frame-score game-score]]))

(testing "Correctly detects a strike"
  (is (= (strike? [10 0]) true))
  (is (= (strike? [0 10]) false))
  (is (= (strike? [5 5]) false)))

(testing "Correctly detects a spare"
  (is (= (spare? [10 0]) false))
  (is (= (spare? [0 10]) true))
  (is (= (spare? [5 5]) true))
  (is (= (spare? [9 1]) true)))

(testing "Frame score for a non-bonus score"
  (is (= (frame-score [1 1] [0]) 2))
  (is (= (frame-score [0 2] [0]) 2))
  (is (= (frame-score [5 4] [0]) 9)))

(testing "Frame score for a spare"
  (is (= (frame-score [0 10] [5 5]) 15))
  (is (= (frame-score [0 10] [0 0]) 10))
  (is (= (frame-score [5 5] [5 0]) 15)))

(testing "Frame score for a strike"
  (is (= (frame-score [10 0] [0 0]) 10))
  (is (= (frame-score [10 0] [5 5]) 20))
  (is (= (frame-score [10 0] [10 0]) 20)))

(testing "A game of 20 single-pin rolls results in a score of 20"
  (is (= (game-score (vec (repeat 20 1))) 20)))

(testing "2 rolls of 5 and 18 rolls of 1 results in a score of 29"
  (is (= (game-score (vec (concat [5 5] (vec (repeat 18 1))))) 29)))

(testing "1 roll of 10 and 18 rolls of one results in a score of 30"
  (is (= (game-score (vec (concat [10 nil] (vec (repeat 18 1))))) 30)))

(testing "2 rolls of 10 and 16 rolls of 1 results in a score of 49"
  (is (= (game-score (vec (concat [10 nil 10 nil] (vec (repeat 16 1))))) 49)))

(testing "3 rolls of 10 and 14 rolls of 1 results in a score of 77"
  (is (= (game-score (vec (concat [10 nil 10 nil 10 nil] (vec (repeat 14 1))))) 77)))

(testing "A roll of 3, a roll of 7; a roll of 3, a roll of 7; 16 rolls of 1 results in a score of 40"
  (is (= (game-score (vec (concat [3 7 3 7] (vec (repeat 16 1))))) 40)))

(testing "18 rolls of 1 and three rolls of 10 results in a score of 48"
  (is (= (game-score (vec (concat (vec (repeat 18 1)) [10 nil 10 10]))) 48)))

(testing "18 rolls of 1; a roll of 3 a 7 and 1 results in a score of 29"
  (is (= (game-score (vec (concat (vec (repeat 18 1)) [3 7 1]))) 29)))