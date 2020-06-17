// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.lang.Math;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.*;
import com.google.sps.Event;
import com.google.sps.Events;
import com.google.sps.MeetingRequest;
import com.google.sps.TimeRange;
import java.util.Collection;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    
    Collection<String> attendees = request.getAttendees();
    ArrayList<TimeRange> invalidTimes = new ArrayList<>();
    int fullDay = TimeRange.WHOLE_DAY.duration();

    if(attendees.size() == 0) attendees = request.getOptionalAttendees();   
    if (request.getDuration() > fullDay) return Arrays.asList();


    invalidTimes = generateInvalidTimes(events,attendees);
    invalidTimes = resolveOverlaps(invalidTimes);

    ArrayList<TimeRange> validTimes = generateValidTimes(invalidTimes, request.getDuration());
    ArrayList<TimeRange> invalidOptionalTimes = generateInvalidTimes(events, request.getOptionalAttendees());

    
    for(TimeRange invalid: invalidOptionalTimes) {
      for(TimeRange valid : validTimes) {
        if(invalid.equals(valid)) validTimes.remove(invalid);
      }
    }
    TimeRange[] returnValidTimes = new TimeRange[validTimes.size()];
    returnValidTimes = validTimes.toArray(returnValidTimes);

    return Arrays.asList(returnValidTimes);
  }

  public ArrayList<TimeRange> resolveOverlaps(ArrayList<TimeRange> ranges) {
    ListIterator<TimeRange> iterator = ranges.listIterator();

    if(iterator.hasNext()){
      TimeRange rangeOne = iterator.next();

      if(iterator.hasNext()){
        TimeRange rangeTwo = iterator.next();

        if(rangeOne.equals(rangeTwo)){}

        else{
          if(rangeOne.overlaps(rangeTwo)){
            int start = Math.min(rangeOne.start(), rangeTwo.start());
            int end = Math.max(rangeOne.end(), rangeTwo.end());
            int replaceIndex = ranges.indexOf(rangeOne);

            TimeRange combinedRange = TimeRange.fromStartEnd(start, end, false);

            ranges.set(replaceIndex, combinedRange);
            ranges.remove(rangeTwo);
          }
        }
      }
    }
    return ranges;
  }

  public ArrayList<TimeRange> generateValidTimes(ArrayList<TimeRange> ranges, long duration) {
    int startTime = TimeRange.START_OF_DAY;
    ArrayList<TimeRange> validTimes = new ArrayList<>();

    for(TimeRange range : ranges) {
      TimeRange valid = TimeRange.fromStartEnd(startTime, range.start(), false);
      if( (long) valid.duration() >= duration) {
        validTimes.add(valid);
      }
      startTime = range.end();
    }
    TimeRange finalRange = TimeRange.fromStartEnd(startTime, TimeRange.END_OF_DAY, true);
    if(finalRange.duration() != 0 ){
      validTimes.add(finalRange);
    }
    return validTimes;
  }

  public ArrayList<TimeRange> generateInvalidTimes(Collection<Event> events, Collection<String> attendees) {
    ArrayList<TimeRange> times = new ArrayList<>();

    for(String attendee : attendees){
      for(Event event: events){
        if(event.getAttendees().contains(attendee)) {
          times.add(event.getWhen());
        }
      }
    }
    return times;
  }
}
