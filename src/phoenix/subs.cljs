(ns phoenix.subs
  (:require
   [phoenix.state :as state]
   [phoenix.helpers :as helpers]))


(def monthly-limit 10000)

(defn get-aggregated-amounts-for-each-month
  "Returns data in format {'January' 1000}"
  [records-by-id]
  (let [months-and-amounts (reduce-kv
                            (fn [acc _ current-item]
                              (let [amount (:amount current-item)
                                    date (:date current-item)
                                    month (helpers/get-month-as-string date)]
                                (if (get acc month)
                                  (update acc month
                                          (fn [current-amount]
                                            (+ current-amount amount)))
                                  (assoc acc month amount))))
                            {}
                            records-by-id)]
    months-and-amounts))

(defn get-aggregated-monthly-amounts-for-chart
  "Returns data in format {:x 'January' :y 1000}"
  [records-by-id]
  (let [months-and-amounts (get-aggregated-amounts-for-each-month records-by-id)]
    (map (fn [[month amount]]
           {:x month :y amount})
         months-and-amounts)))

(defn get-aggregated-amount-for-current-month [records-by-id date]
  (let [months-and-amounts (get-aggregated-amounts-for-each-month records-by-id)
        current-month (helpers/get-month-as-string date)]
    (get months-and-amounts current-month)))

(defn get-fraction-used [records-by-id date monthly-limit]
  (let [current-month-amount-spent (get-aggregated-amount-for-current-month records-by-id date)]
    (/ current-month-amount-spent monthly-limit)))

(state/register-subscription
 :get-name-from-new-record
 (fn [state]
   (get-in state [:new-record :name])))

(state/register-subscription
 :get-amount-from-new-record
 (fn [state]
   (get-in state [:new-record :amount])))

(state/register-subscription
 :get-records-by-order
 (fn [state]
   (:records-by-order state)))

(state/register-subscription
 :get-name
 (fn [state id]
   (get-in state [:records-by-id id :name])))

(state/register-subscription
 :get-amount
 (fn [state id]
   (get-in state [:records-by-id id :amount])))

(state/register-subscription
 :get-currency
 (fn [state id]
   (get-in state [:records-by-id id :currency])))

(state/register-subscription
 :get-month
 (fn [state id]
   (let [date (get-in state [:records-by-id id :date])]
     (.getMonth date))))

(state/register-subscription
 :get-fraction-used
 (fn [state]
   (get-fraction-used (:records-by-id state) (js/Date.) monthly-limit)))

(state/register-subscription
 :get-aggregated-monthly-amounts-for-chart
 (fn [state]
   (get-aggregated-monthly-amounts-for-chart (:records-by-id state))))

(state/register-subscription
 :get-aggregated-amount-for-current-month
 (fn [state]
   (get-aggregated-amount-for-current-month (:records-by-id state) (js/Date.))))

(state/register-subscription
 :get-current-month
 (fn [state]
   (:current-month state)))
