(ns todo-cljs.views
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as reagent :refer [atom]]
   [todo-cljs.events :as events]
   [todo-cljs.subs :as subs]
   [clojure.string :as string]))

(defn- item
  "A todo item"
  [id text done?]
  [:li {:key id :on-click #(re-frame/dispatch [::events/toggle-todo id])}
   (if done? [:strike.has-text-grey-lighter text] text)])

(defn- todo-items
  [todos]
  (map
   (fn
     [[id {:keys [title done?]}]]
     (item id title done?))
   todos))

(defn- todos-control
  "Control for viewing todos"
  [todos]
  [:div.panel-block
   [:ul
    (todo-items todos)]])

(defn- new-todo-control
  "Control for adding a new todo"
  []
  (let [title (atom "")
        disabled? (atom true)]
    (fn []
      [:div.panel-block
       [:p.control.mr-4
        [:input.input {:type "text"
                       :value @title
                       :placeholder "Do things..."
                       :on-change (fn [elem]
                                    (let [text-value (-> elem .-target .-value)
                                          is-blank? (string/blank? text-value)]
                                      (reset! title text-value)
                                      (reset! disabled? is-blank?)))}]]
       [:button.button.is-primary {:on-click #(do (re-frame/dispatch [::events/add-todo @title])
                                                  (reset! title nil))
                                   :disabled @disabled?} "+"]])))

(defn main-panel []
  (let [todos (re-frame/subscribe [::subs/todos])]
    [:div.section
     [:div.container
      [:div.panel
       [:p.panel-heading "TODO"]
       (todos-control @todos)
       [new-todo-control]]]]))

(comment
  (str (random-uuid)))
