# spring-cloud-sleuth-learning
sleuth working + traceId + spanId + baggage

Send request "http://localhost:8081/client/getData" with header "X-B3-TripId" : "0123"
OR

"curl -H "X-B3-TripId: Q0123456" http://localhost:8081/client/getData"

