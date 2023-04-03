package com.grammar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
* <h1>TxtFileLog!</h1>
*
* The TxtFileLog class implements an application that performs operations on files.
*
* @author  Vitor Emanuel
* @version 1.0
* @since   2022-12-01
*/
public class TxtFileLog {
    
  private FileReader arq;
  private BufferedReader inFile;
  private String nameOutFile;
  private BufferedWriter outFile;

  /**
  * Opens an input file and starts an output file.
  * 
  * @param fileIn input file name to be opened.
  * @param fileOut input file name to be started.
  */
  public TxtFileLog(String fileIn, String fileOut){
      openInFile(fileIn);
      startOutFile(fileOut);
  }
  
  /**
  * Initializes the output file, preparing it for future writes.
  * @param fileOut path to the output file.
  */  
  private void startOutFile(String fileName){
    try {
      nameOutFile = fileName;
      outFile = new BufferedWriter(new FileWriter(nameOutFile, false));
      closeOutFile();
    } catch (IOException e) {}
  }
  
  /**
  * Opens the input file.
  * @param fileIn path to the input file.
  */ 
  private void openInFile(String fileName){
    try {
        
      arq = new FileReader(fileName);
      inFile = new BufferedReader(arq);
    } catch (IOException e) {}
    
  }
  
  /**
  * Open the output file.
  */ 
  private void openOutFile(){
    try {
      outFile = new BufferedWriter(new FileWriter(nameOutFile, true));
      
    } catch (IOException e) {}
  }
  
  /**
  * Reads a line from the input file.
  * @return String Line read from the input file.
  */ 
  public String read(){
      try{
        return inFile.readLine();
      } catch (IOException e) {
        return null;
      }
  }
  
  /**
  * Writes a string to the output file.
  * @param string string to be written.
  */ 
  public void write(String string){
      try{
        openOutFile();
        outFile.write(string);
        closeOutFile();
      } catch (IOException e) {
      }
  }
  
  /**
  * Writes a line break to the output file.
  */ 
  public void newLine(){
      write("\n");
  }
  
  /**
  * Closes the input file.
  */ 
  private void closeInFile(){
    try{
      inFile.close();
    } catch (IOException e) {
    }  
      
  }
  
  /**
  * Closes the output file.
  */ 
  private void closeOutFile(){
    try{
      outFile.close();
    } catch (IOException e) {
    }  
  }
  
  /**
  * Closes all the files.
  */ 
  public void closeFiles(){
    closeInFile();
    closeOutFile();
  }
}
