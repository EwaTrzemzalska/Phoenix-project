(ns phoenix.fake-data)

(defn generate-fake-starting-state []
  (let [id1 (random-uuid)
        id2 (random-uuid)
        id3 (random-uuid)]
    {:new-record nil
     :records-by-id {id1 {:id id1
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
                          :date (js/Date. 2020 2 17)}}
     :records-by-order [id1 id2 id3]}))
