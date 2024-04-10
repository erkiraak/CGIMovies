// package com.erkiraak.movies.util;

// import java.util.ArrayList;
// import java.util.List;

// import com.erkiraak.movies.entity.Room;
// import com.erkiraak.movies.entity.Session;

// public class SeatFinder {

//     public static List<int[]> findBestSeats(Session session, int numberOfSeats) {
//         Room room = session.getRoom();
//         int rows = room.getRows();
//         int cols = room.getSeatsPerRow();

//         List<int[]> bestSeats = new ArrayList<>();
//         int maxScore = Integer.MIN_VALUE;

//         for (int i = 0; i < rows; i++) {
//             for (int j = 0; j <= cols - numberOfSeats; j++) {
//                 boolean allSeatsAvailable = true;
//                 for (int k = 0; k < numberOfSeats; k++) {
//                     if (!session.getSeatReservationArray()[i][j + k]) {
//                         allSeatsAvailable = false;
//                         break;
//                     }
//                 }
//                 if (allSeatsAvailable) {
//                     int[] seatGroup = new int[n];
//                     for (int k = 0; k < n; k++) {
//                         seatGroup[k] = seatWeightsArray[i][j + k];
//                     }
//                     int score = calculateScore(seatGroup);
//                     if (score > maxScore) {
//                         maxScore = score;
//                         bestSeats.clear();
//                         for (int k = 0; k < n; k++) {
//                             bestSeats.add(new int[]{i, j + k});
//                         }
//                     }
//                 }
//             }
//         }
//         return bestSeats;
//     }

//     private static int calculateScore(int[] seatGroup) {
//         int score = 0;
//         for (int weight : seatGroup) {
//             score += weight;
//         }
//         return score;
//     }

// }
