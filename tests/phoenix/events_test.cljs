(ns phoenix.events-test
  (:require
   [cljs.test :refer-macros [deftest is testing run-tests]]
   [phoenix.events
    :as events]))


(deftest add-record-test
  (let [id (random-uuid)
        date (js/Date.)
        state {:new-record nil
               :records-by-id {1 {:id 1
                                  :currency "UAH"
                                  :name "John"
                                  :amount 1000
                                  :date date}}
               :records-by-order [1]}]
    (with-redefs [random-uuid (constantly id)
                  js/Date (constantly date)]
      (is (= {:records-by-id {id {:id id
                                  :currency "UAH"
                                  :date date
                                  :name "Jimmy"
                                  :amount 200}}
              :records-by-order [id]}
             (events/add-record {} {:name "Jimmy"
                                    :amount "200"})))
      (is (= {:new-record nil
              :records-by-id {1 {:id 1
                                 :currency "UAH"
                                 :name "John"
                                 :amount 1000
                                 :date date}
                              id {:id id
                                  :currency "UAH"
                                  :name "Zygmunt"
                                  :amount 3099
                                  :date date}}
              :records-by-order [1 id]}
             (events/add-record state
                                {:name "Zygmunt"
                                 :amount "3099"}))))))
