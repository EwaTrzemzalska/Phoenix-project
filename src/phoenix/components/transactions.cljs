(ns phoenix.components.transactions
  (:require
   [phoenix.state :as state]))

(defn table-of-transactions []
  (let [records-by-order (state/subscribe :get-records-by-order)]
    [:table.table.is-bordered
     [:thead
      [:tr
       [:th {:colSpan 2}
        "Table of transactions"]]
      [:tr
       [:th "Name"]
       [:th "Amount"]]]
     [:tbody
      (doall (for [id records-by-order]
               [:tr {:key id}
                [:td (state/subscribe :get-name id)]
                [:td (state/subscribe :get-amount id)
                 " " (state/subscribe :get-currency id)]]))]]))
