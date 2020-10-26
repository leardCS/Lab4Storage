import java.io.*;
import java.util.*;
import javax.swing.*;

public class Lab4 {

	public static void main(String[] args) throws FileNotFoundException {
		
		String line = "";
		BufferedReader br = null;
		String cvsSplitBy = ",";
		String[] songTitle = new String[2600];
		String[] chartFiles = {"7-09.csv", "7-16.csv", "7-23.csv", "7-30.csv", "8-06.csv", "8-13.csv", "8-20.csv", "8-28.csv", "9-03.csv", "9-10.csv", "9-17.csv", "9-24.csv", "10-1.csv"};
		String[] songArtist = new String[2600];
		ArrayList<String> sorted = new ArrayList<String>();
		String currentSong;
		String userInput = null;
		
		//Reading data from multiple CSV files to an array to avoid losing data in transfer process
		//Transferring data from Array to ArrayList to be able to remove duplicate entries
		int x = 0;
		try {
			for(int i=0; i<13; i++) {
				br = new BufferedReader(new FileReader(chartFiles[i]));
				while ((line = br.readLine()) != null) {
					String[] name = line.split(cvsSplitBy);
					songTitle[x] = name[1];
					songArtist[x] = name[2];
					sorted.add(songTitle[x] + " by " + songArtist[x]);		
					x++;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
						}
					}
				}
		
		//Removing duplicate entries in the ArrayList
		Set<String> set = new HashSet<>(sorted);
		sorted.clear();
		sorted.addAll(set);
		String[] sortedArr = sorted.toArray(new String[sorted.size()]);
		
		
		//Listening to the Top 200 Charts in the United States during Q3, creating a playlist & track listening history
	    try {
	        FileWriter Output = new FileWriter("Report.txt");
	        FileWriter Output2 = new FileWriter("ListeningHistory.txt");
	        Output.write("Your finalized playlist of the Spotify Top 200 Charts in the United States during Q3(July, August, September): \n" 
	        		   + "-------------------------------------------------------------------------------------------------------------- \n");
	        Output2.write("Your listening history of the Spotify Top 200 Charts in the United States during Q3(July, August, September): \n"
				    + "------------------------------------------------------------------------------------------------------------- \n");
	        
			for(int i=0; i<420; i++) {
					currentSong = sortedArr[i];
					Output2.write("Track #" + (i+1) + ": " + currentSong + "\n");
					userInput = JOptionPane.showInputDialog("Now playing: " + currentSong + "\n\nEnter \"skip\" to skip to the next track or \"add\" to add the current track to your playlist:"
														   +"\n\nTo finish, enter \"done\".");
					if(userInput.equalsIgnoreCase("skip")) continue;
					else if(userInput.equalsIgnoreCase("add")) Output.write("Track #" + (i+1) + ": " + sortedArr[i] + "\n");
					else if(userInput.equalsIgnoreCase("done")) break;
					else if(userInput.equals(null)) break;
					while((!userInput.equalsIgnoreCase("skip")) && (!userInput.equalsIgnoreCase("add")) && (!userInput.equalsIgnoreCase(null))) {
							userInput = JOptionPane.showInputDialog("Enter a valid input!\n\n" + "Currently playing: " + currentSong 
																  + "\n\nEnter \"skip\" to skip to the next track or \"add\" to add the current track to your playlist:");
							if(userInput.equalsIgnoreCase("skip")) continue;
							else if(userInput.equalsIgnoreCase("add")) Output.write("Track #" + (i+1) + ": " + sortedArr[i] + "\n");
							else if(userInput.equalsIgnoreCase("done")) break;
							else if(userInput.equals(null)) break;
						}
					}
	        Output.close();
	        Output2.close();
	        System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      } catch (NullPointerException k) {
	    	JOptionPane.showMessageDialog(null, "Playlist creation cancelled by user.");
	      }
	    }
	}