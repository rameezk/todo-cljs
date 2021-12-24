(ns todo-cljs.db)

(def default-db
  {:todos {"1" {:title "Buy milk" :done? false}
           "2" {:title "Pay bills" :done? true}}})
