(ns phoenix.subs-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [phoenix.subs :as subs]))


(defn generate-state []
  (let [id1 (random-uuid)
        id2 (random-uuid)
        id3 (random-uuid)
        id4 (random-uuid)]
    {id1 {:id id1
          :currency "UAH"
          :name "John"
          :amount 1000
          :date (js/Date. 2020 0 17)}
     id2 {:id id2
          :currency "UAH"
          :name "Jim "
          :amount 299
          :date (js/Date. 2020 1 17)}
     id3 {:id id3
          :currency "UAH"
          :name "Jake"
          :amount 399
          :date (js/Date. 2020 2 17)}
     id4 {:id id4
          :currency "UAH"
          :name "Jake"
          :amount 2000
          :date (js/Date. 2020 2 18)}}))

(deftest get-aggregated-monthly-amounts-for-chart-test
  (is (= []
         (subs/get-aggregated-monthly-amounts-for-chart {})))
  (is (= [{:x "January" :y 1000}
          {:x "February" :y 299}
          {:x "March" :y 2399}]
         (subs/get-aggregated-monthly-amounts-for-chart (generate-state)))))

(deftest get-aggregated-amounts-for-each-month-test
  (is (= {}
         (subs/get-aggregated-amounts-for-each-month {})))
  (is (= {"January" 1000
          "February" 299
          "March" 2399}
         (subs/get-aggregated-amounts-for-each-month (generate-state)))))

(deftest get-aggregated-amount-for-current-month-test
  (is (= nil
         (subs/get-aggregated-amount-for-current-month {} (js/Date. 2020 0 17))))
  (is (= 1000
         (subs/get-aggregated-amount-for-current-month (generate-state) (js/Date. 2020 0 17)))))

(deftest get-fraction-used-test
  (is (= 0
         (subs/get-fraction-used {} (js/Date. 2020 0 17) 1000)))
  (is (= 0.1
         (subs/get-fraction-used (generate-state) (js/Date. 2020 0 17) 10000))))
