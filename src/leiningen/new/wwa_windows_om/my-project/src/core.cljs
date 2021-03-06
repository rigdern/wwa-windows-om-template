(ns {{name}}.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

;; For an introduction to the Blank template, see the following documentation:
;; http://go.microsoft.com/fwlink/?LinkId=232509
(let [app (.. js/WinJS -Application)
      activation (.. js/Windows -ApplicationModel -Activation)]

  (set! (.-onactivated app)
        (fn [args]
          (when (= (.. args -detail -kind) (.. activation -ActivationKind -launch))
            (if (not= (.. args -detail -previousExecutionState) (.. activation -ApplicationExecutionState -terminated))
              (comment
                "TODO: This application has been newly launched. Initialize"
                "your application here.")
              (comment
                "TODO: This application has been reactivated from suspension."
                "Restore application state here."))
            (.setPromise args (.processAll (.-UI js/WinJS))))))

  (set! (.-oncheckpoint app)
        (fn [args]
          ;; TODO: This application is about to be suspended. Save any state
          ;; that needs to persist across suspensions here. You might use the
          ;; WinJS.Application.sessionState object, which is automatically
          ;; saved and restored across suspension. If you need to complete an
          ;; asynchronous operation before your application is suspended, call
          ;; args.setPromise().
          ))

  (.start app))

(om/root
  (fn [app owner]
    (reify om/IRender
      (render [_]
        (dom/h1 nil (:text app)))))
  app-state
  {:target (. js/document (getElementById "app"))})
