package com.stackroute.streams;

import java.util.*;
import java.util.stream.Collectors;

public class BatsmanService {

    private List<Batsman> batsmanList = new ArrayList<>();

    public Optional<Batsman> getBatsman(List<Batsman> batsmanList, String name, String countryCode) throws CountryNotFoundException {
        if(batsmanList == null) {
            return Optional.empty();
        }
        if (!batsmanList.isEmpty() && name != null && countryCode != null) {
            List<Batsman> bastman = batsmanList
                    .stream()
                    .filter(batsman -> batsman.getCountry().getCountryCode().equalsIgnoreCase(countryCode))
                    .collect(Collectors.toList());
            if(bastman.isEmpty()) {
                throw new CountryNotFoundException();
            } else {
//                bastman = batsmanList.stream().filter(batsman -> batsman
//                        .getName()
//                        .equalsIgnoreCase(name))
//                        .collect(Collectors.toList());
//                Batsman result = bastman.get(0);
//                return Optional.of(result);
                Optional<Batsman> result = batsmanList
                        .stream()
                        .filter(batsman -> batsman.getName().equalsIgnoreCase(name))
                        .findFirst();
                return result;
            }

        } else {
            return Optional.empty();
        }

    }

    public String getBatsmanNamesForCountry(List<Batsman> batsmanList, String countryCode) throws CountryNotFoundException {
        if (batsmanList == null || batsmanList.isEmpty() || countryCode == null) {
            return null;
        } else {
            return "[" + batsmanList.stream()
                    .filter(batsman -> batsman.getCountry().getCountryCode().equalsIgnoreCase(countryCode))
                    .sorted(Comparator.comparing(Batsman::getName))
                    .map(Batsman::getName).collect(Collectors.joining(",")) + "]";
        }
    }
    public Map<String, Integer> getPlayerNameWithTotalRuns(List<Batsman> batsmanList) {
        if(batsmanList == null) {
            return new HashMap<>();
        }
        if(!batsmanList.isEmpty()) {
            Map<String, Integer> map = new HashMap<>();
            for(Batsman b : batsmanList) {
                if(b.getTotalRuns() > 50) {
                    map.put(b.getName(),b.getTotalRuns());
                }
            }
            return (map);
        } else {
            return new HashMap<>();
        }

    }

    public int getHighestRunsScoredByBatsman(List<Batsman> batsmanList, String countryName) {
        if(batsmanList == null || countryName == null || batsmanList.isEmpty()) {
            return 0;
        }
        List<Batsman> batsmanFiltered = batsmanList
                .stream()
                .filter(batsman -> batsman.getCountry().getName().equalsIgnoreCase(countryName))
                .collect(Collectors.toList());
        List<Integer> batsmanHighestRuns = new ArrayList<>();
        for(Batsman b : batsmanFiltered) {
            batsmanHighestRuns.add(b.getHighestScore());
        }
        return Collections.max(batsmanHighestRuns);
    }

    public Optional<List<String>> getPlayerNamesByCountry(List<Batsman> batsmanList, String countryName) throws CountryNotFoundException{
        if(batsmanList == null || countryName == null) {
            return Optional.empty();
        }
            List<Batsman> batsmanFiltered = batsmanList
                    .stream()
                    .filter(batsman -> batsman.getCountry().getName().equalsIgnoreCase(countryName))
                    .filter(batsman -> batsman.getTotalRuns() > 5000)
                    .collect(Collectors.toList());

            if(!batsmanFiltered.isEmpty()) {
                List<String> batsmanNames = new LinkedList<>();
                for(Batsman b : batsmanFiltered) {
                    batsmanNames.add(b.getName());
                }
                batsmanNames.sort(Comparator.reverseOrder());
                return Optional.of(batsmanNames);
            } else {
                return Optional.empty();
            }

        }

    }

