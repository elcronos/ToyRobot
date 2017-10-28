(ns .toy)
;; Toy Robot Simulator
;; Camilo Pestana. 2017
;; github.com/elcronos

(def max-size-table 5)                              ;; N = 5, where NxN is the size of the square table
(def valid-faces ["NORTH" "EAST" "SOUTH" "WEST"])   ;; Valid faces for robot
(def state (atom {}))                               ;; Mutable map
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn parse-int [s] (Integer. s))
(defn get-state [key] (@state key))
(defn empty-state [] (empty? @state))
(defn some-not-nil [val] (not (nil? val)))
(defn check-positive [number] (>= number 0))
(defn update-state [key val] (swap! state assoc key val))
(defn get-args [string] (clojure.string/split string #","))
(defn call-func [name &args] ((resolve (symbol name)) &args))
(defn execute-command ([func, args] (call-func func args)))
(defn check-max-position [number] (< number max-size-table))
(defn tokenize-spaces [string] (clojure.string/split string #"\s+"))
(defn check-valid-face [face] (some-not-nil(some #{face} valid-faces)))
(defn valid-position [number] (and (check-positive number) (check-max-position number)))
(defn check-args-place [v] (and (= (count (tokenize-spaces v)) 2) (= (first (tokenize-spaces v)) "PLACE")))
(defn first-arg-lower [args] (clojure.string/lower-case (first (tokenize-spaces args))))
(defn first-arg-upper [args] (clojure.string/upper-case (first (tokenize-spaces args))))
(defn second-args [args] (get-args (get (tokenize-spaces args) 1)))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; TOY ROBOT COMMANDS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn report [args] (println "Output:" (clojure.string/trim (str (get-state ":x") "," (get-state ":y") "," (get-state ":f")))))

(defn place [args]
  (let [x (get args 0)] (let [y (get args 1)] (let [f (get args 2)]
  (when (and (valid-position (parse-int x)) (valid-position (parse-int y)) (check-valid-face f))
    (update-state ":x" (parse-int x))
    (update-state ":y" (parse-int y))
    (update-state ":f" f))))))

(defn right [args]
  (let [index-current (.indexOf valid-faces (get-state ":f"))]
    (let [right-index (+ index-current 1)]
      (cond
        (= right-index 4) (update-state ":f" (first valid-faces))                 ;; NORTH
        (and (> right-index 0) (< right-index 4)) (update-state ":f" (get valid-faces right-index))))))

(defn left [args]
  (let [index-current (.indexOf valid-faces (get-state ":f"))]
    (let [left-index (- index-current 1)]
      (cond
        (= left-index -1) (update-state ":f" (last valid-faces))                 ;; EAST
        (> left-index -1) (update-state ":f" (get valid-faces left-index))))))

(defn move [args]
  (let [current-direction (get-state ":f")]
    (cond
      (= current-direction (get valid-faces 0)) (place [(get-state ":x") (+ (get-state ":y") 1) (get-state ":f")])  ;; NORTH
      (= current-direction (get valid-faces 1)) (place [(+ (get-state ":x") 1)  (get-state ":y") (get-state ":f")]) ;; EAST
      (= current-direction (get valid-faces 2)) (place [(get-state ":x")  (- (get-state ":y") 1) (get-state ":f")]) ;; SOUTH
      (= current-direction (get valid-faces 3)) (place [(- (get-state ":x") 1) (get-state ":y") (get-state ":f")])  ;; WEST
      )))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn input []
  (println "Toy robot ready!")
  (loop []
    (if-let [v (read-line)]                                 ;; Read line from console
      (when (not= v "EXIT")                                 ;; Execute command unless command is EXIT
        (cond
          (and (empty-state) (check-args-place v)) (execute-command (first-arg-lower v) (second-args v))
          (not (empty-state)) (execute-command (first-arg-lower v) nil))
        (recur)))))

(input)
