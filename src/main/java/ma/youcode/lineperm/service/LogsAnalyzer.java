package ma.youcode.lineperm.service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ma.youcode.lineperm.models.Logs;

public class LogsAnalyzer {
      List<Logs> logsList;

      public LogsAnalyzer(List<Logs> logsList){
        this.logsList = logsList;
      }

      public long totalAction(){
        return logsList.stream().count();
      }

      public long totalRefuse(){
        return logsList.stream().filter(logs -> logs.getResult() == Logs.Status.REFUSE).count();
      }

      public List<String> distinct(){
        return logsList.stream().map(l -> l.getUser()).distinct().toList();
      }

      public Map<String, Long> userAction(){
        return logsList.stream().collect(Collectors.groupingBy(l -> l.getUser(), Collectors.counting()));
      }

      public List<String> Top3Files(){
        return logsList.stream().collect(Collectors.groupingBy(l -> l.getFile(), Collectors.counting()))
        .entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(3)
        .map(Map.Entry :: getKey).collect(Collectors.toList());
      }

      public long RefusedByUser(String username){
        return logsList.stream().filter(l -> l.getUser().equals(username))
        .filter(l -> l.getResult() == Logs.Status.REFUSE).count();
      }

      public List<String> MostActive(){
        return logsList.stream().collect(Collectors.groupingBy(l -> l.getUser(), Collectors.counting()))
        .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .limit(1).map(Map.Entry :: getKey).collect(Collectors.toList());
      }

      public Map<Logs.LogsType, Long> ActionCount(){
        return logsList.stream().collect(Collectors.groupingBy(l -> l.getAction(), Collectors.counting()));

      }


}