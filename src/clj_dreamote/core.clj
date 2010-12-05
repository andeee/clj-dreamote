(ns clj-dreamote.core
  (:require [clj-http.client :as client])
  (:require [clojure.contrib.except :as except]))

(def remote
  {                         :power 116
                                      
 :1   2          :2  3          :3   4
 :4   5          :5  6          :6   7
 :7   8          :8  9          :9  10
 :< 412          :0 11          :> 407
                                      
:volup 115    :mute 113     :bqtup 402
               :exit 1 
  :voldown 114          :bqtdown 403  
                                      
:info  358    :up   103     :menu  141
:left  105    :ok   352     :right 106
:audio 392    :down 108     :video 393

:red 398 :green 399 :yellow 400 :blue 401
:rew 168 :play  207 :pause  119 :fwd  208

:tv    385 :radio 377 :text 66  :help 138
:sharp 128 :O     167})

(def *dreamote-conn* {:host "ip-or-hostname" :auth ["user" "pwd"]})

(defn send-key! [keycode]
  (client/get (str "http://" (:host *dreamote-conn*) "/cgi-bin/rc?" keycode)
	      {:basic-auth (:auth *dreamote-conn*)}))

(defn to-keyword [button-form]
  (cond
   (symbol? button-form) (keyword (name button-form))
   (keyword? button-form) button-form
   :else (keyword (str button-form))))

(defn to-keycode [button-form]
  (let [button-keyword (to-keyword button-form)]
    (except/throw-if (not (contains? button-keyword remote))
		     (str "button " (name button-keyword) " doesn't exist on remote"))
    (get remote button-keyword)))

(defn press
  ([button] (press button 1))
  ([button times]
     (dotimes [_ times]
       (send-key! (to-keycode button)))))

(defmacro with-remote [dreamote-conn & forms]
  `(binding [*dreamote-conn* ~dreamote-conn]
     (do ~@forms)))