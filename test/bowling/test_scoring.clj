(ns bowling.test-scoring
  (:require
    [clojure.test :refer :all]
    [bowling.scoring :as scoring]))

(deftest strikes
  (testing "Correctly detects a strike"
    (is (= (scoring/strike? [10]) true))
    (is (= (scoring/strike? [0 10]) false))
    (is (= (scoring/strike? [5 5]) false))))

(deftest spares
  (testing "Correctly detects a spare"
    (is (= (scoring/spare? [10]) false))
    (is (= (scoring/spare? [0 10]) true))
    (is (= (scoring/spare? [5 5]) true))
    (is (= (scoring/spare? [9 1]) true))))

(deftest roll-scoring
  (testing "Score for a non-bonus score"
    (is (= (scoring/frame-score-and-rolls [1 1]) [2 '()]))
    (is (= (scoring/frame-score-and-rolls [0 2]) [2 '()]))
    (is (= (scoring/frame-score-and-rolls [5 4]) [9 '()])))

  (testing "Frame score for a spare"
    (is (= (scoring/frame-score-and-rolls [0 10 5 5]) [15 '(5 5)]))
    (is (= (scoring/frame-score-and-rolls [0 10 0 0]) [10 '(0 0)]))
    (is (= (scoring/frame-score-and-rolls [5 5 5 0]) [15 '(5 0)])))

  (testing "Frame score for a strike"
    (is (= (scoring/frame-score-and-rolls [10 0 0 0]) [10 '(0 0 0)]))
    (is (= (scoring/frame-score-and-rolls [10 0 5 5]) [15 '(0 5 5)]))
    (is (= (scoring/frame-score-and-rolls [10 0 10 0]) [20 '(0 10 0)]))))

(deftest game-scoring

  (testing "A game of 20 single-pin rolls results in a score of 20"
    (is (= (scoring/game-score (repeat 20 1)) 20)))

  (testing "2 rolls of 5 and 18 rolls of 1 results in a score of 29"
    (is (= (scoring/game-score (concat [5 5] (vec (repeat 18 1)))) 29)))

  (testing "1 roll of 10 and 18 rolls of one results in a score of 30"
    (is (= (scoring/game-score (concat [10] (repeat 18 1))) 30)))

  (testing "2 rolls of 10 and 16 rolls of 1 results in a score of 49"
    (is (= (scoring/game-score (vec (concat [10 10] (repeat 16 1)))) 49)))

  (testing "3 rolls of 10 and 14 rolls of 1 results in a score of 77"
    (is (= (scoring/game-score (vec (concat [10 10 10] (repeat 14 1)))) 77)))

  (testing "A roll of 3, a roll of 7; a roll of 3, a roll of 7; 16 rolls of 1 results in a score of 40"
    (is (= (scoring/game-score (concat [3 7 3 7] (repeat 16 1))) 40)))

  (testing "18 rolls of 1 and three rolls of 10 results in a score of 48"
    (is (= (scoring/game-score (concat (repeat 18 1) [10 10 10])) 48)))

  (testing "18 rolls of 1; a roll of 3 a 7 and 1 results in a score of 29"
    (is (= (scoring/game-score (concat (repeat 18 1) [3 7 1])) 29)))

  (testing "A 'perfect' game results in a score of 300"
    (is (= 300 (scoring/game-score (repeat 12 10))))))
