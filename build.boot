
(def project "system-spec")
(def version "0.1.0")

(set-env!
  :source-paths #{"src"}
  :test-paths #{"test"}
  :dependencies
  '[[org.clojure/tools.namespace "0.2.11"]
    [reloaded.repl "0.2.3" :scope "test"]
    ])


(require ;'[adzerk.boot-cljs :refer [cljs]]
         ;'[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         ;'[adzerk.boot-reload :refer [reload]]
         ;'[deraen.boot-sass :refer [sass]]
         ;'[com.stuartsierra.component :as component]
         'clojure.tools.namespace.repl
         ;'[edge.system :refer [new-system]]
         'boot.task.built-in
         )

(def repl-port 5600)

(task-options!
 repl {:client true
       :port repl-port}
 pom {:project (symbol project)
      :version version
      :description "A complete Clojure project you can leap from"
      :license {"The MIT License (MIT)" "http://opensource.org/licenses/mit-license.php"}}
 aot {:namespace #{'edge.main}}
 jar {:main 'edge.main
      :file (str project "-app.jar")})

(deftask dev-system
  "Develop the server backend. The system is automatically started in
  the dev profile."
  []
  (let [run? (atom false)]
    (with-pass-thru _
      (when-not @run?
        (reset! run? true)
        (require 'reloaded.repl)
        (let [go (resolve 'reloaded.repl/go)]
          (try
            (require 'user)
            ;(go)
            (catch Exception e
              (boot.util/fail "Exception while starting the system\n")
              (boot.util/print-ex (.getCause e)))))))))

(deftask dev
  "This is the main development entry point."
  []
  (set-env! :dependencies #(vec (concat % '[[reloaded.repl "0.2.1"]])))
  (set-env! :source-paths #(conj % "dev"))

  ;; Needed by tools.namespace to know where the source files are
  (apply clojure.tools.namespace.repl/set-refresh-dirs (get-env :directories))

  (comp
   (watch)
   (speak)
   (boot.task.built-in/repl
     :server true
     :client false
     :port repl-port
     :init-ns 'user)
   ;(sass :output-style :expanded)
   ;(reload :on-jsload 'edge.main/init)
   ;(cljs-repl :nrepl-opts {:client false
   ;                        :port repl-port
   ;                        :init-ns 'user}) ; this is also the server repl!
   ;(cljs :ids #{"edge"} :optimizations :none)
   (dev-system)
   (target)))