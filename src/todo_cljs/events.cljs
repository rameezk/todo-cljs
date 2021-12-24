(ns todo-cljs.events
  (:require
   [re-frame.core :as re-frame]
   [todo-cljs.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::toggle-todo
 (fn [db [_ id]]
   (let [done? (get-in db [:todos id :done?])]
     (assoc-in db [:todos id :done?] (not done?)))))

(re-frame/reg-event-db
 ::add-todo
 (fn [db [_ title]]
   (let [id (str (random-uuid))
         existing-todos (:todos db)
         new-todos (assoc existing-todos id {:title title :done? false})]
     (assoc db :todos new-todos))))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))
