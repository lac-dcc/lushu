package com.lushu;

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
  * This is the constructor with parameters using the standard
  * encryption/decryption key, in this all variables are initialized as passed.
  * 
  * @param fileIn This is a String representing the input file.
  * @param fileOut This is a String representing the output file.
  */
  public TxtFileLog(String fileIn, String fileOut){
      openInFile(fileIn);
      startOutFile(fileOut);
  }
  public TxtFileLog(){
      openInFile("./text_files/original.txt");
      startOutFile("./text_files/encrypted.txt");
  }
  /**
  * This method is used to initialize the output file, preparing it for future writes.
  * @param fileOut This is a String representing the output file.
  */  
  private void startOutFile(String fileName){
    try {
      nameOutFile = fileName;
      outFile = new BufferedWriter(new FileWriter(nameOutFile, false));
      closeOutFile();
    } catch (IOException e) {}
  }
  
  /**
  * This method is used to open the input file.
  * @param fileIn This is a String representing the input file.
  */ 
  private void openInFile(String fileName){
    try {
        
      arq = new FileReader(fileName);
      inFile = new BufferedReader(arq);
    } catch (IOException e) {}
    
  }
  
  /**
  * This method is used to open the output file.
  * @param fileOut This is a String representing the input file.
  */ 
  private void openOutFile(){
    try {
      outFile = new BufferedWriter(new FileWriter(nameOutFile, true));
      
    } catch (IOException e) {}
  }
  
  /**
  * This method return the respective line in the read file.
  * @return String This is the line read from the input file.
  */ 
  public String read(){
      try{
        return inFile.readLine();
      } catch (IOException e) {
        return null;
      }
  }
  
  /**
  * This method write a string in the output file.
  * @param string This is a String to be written.
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
  * This method write a line break in the output file.
  */ 
  public void newLine(){
      write("\n");
  }
  
  /**
  * This method closes the input file.
  */ 
  private void closeInFile(){
    try{
      inFile.close();
    } catch (IOException e) {
    }  
      
  }
  
  /**
  * This method closes the output file.
  */ 
  private void closeOutFile(){
    try{
      outFile.close();
    } catch (IOException e) {
    }  
  }
  
  /**
  * This method closes the files.
  */ 
  public void closeFiles(){
    closeInFile();
    closeOutFile();
  }
}
