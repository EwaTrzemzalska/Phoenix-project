(ns phoenix.helpers)

(def months {0 "January"
             1 "February"
             2 "March"
             3 "April"
             4 "May"
             5 "June"
             6 "July"
             7 "August"
             8 "September"
             9 "October"
             10 "November"
             11 "December"})

(defn get-month-as-string [date]
  (get months (.getMonth date)))
