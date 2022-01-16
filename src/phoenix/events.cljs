(ns phoenix.events
  (:require
   [phoenix.state :as state]
   [phoenix.fake-data :as fake]
   [phoenix.helpers :as helpers]))

(state/register-handler
 :add-account
 (fn [state account-id account]
   (assoc state account-id account)))

(state/register-handler
 :assoc-amount
 (fn [state amount]
   (assoc-in state [:new-record :amount] amount)))

(state/register-handler
 :assoc-name
 (fn [state name]
   (assoc-in state [:new-record :name] name)))

(defn add-record [state new-record]
  (let [id (random-uuid)
        amount-parsed (js/parseInt
                       (or (:amount new-record) 0))
        record {:id id
                :currency "UAH"
                :date (js/Date.)
                :name (:name new-record)
                :amount amount-parsed}]
    (-> state
        (update :records-by-id (fn [records]
                                 (assoc records id record)))
        (update :records-by-order (fn [records]
                                    (into [] (conj records id)))))))

(state/register-handler :add-record add-record)

(state/register-handler
 :clear-new-record
 (fn [state]
   (assoc state :new-record nil)))

(state/register-handler
 :init-db
 (fn [state]
   ;; it's for dev only
   (-> (fake/generate-fake-starting-state)
       (assoc :current-month (helpers/get-month-as-string (js/Date.))))))
