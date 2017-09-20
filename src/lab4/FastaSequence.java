package lab4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FastaSequence
{
	private String header;
	private StringBuffer sequence = new StringBuffer();
	
	//constructor
	public FastaSequence( String header, StringBuffer sequence ) 
	{
		
		this.header = header;
		this.sequence = sequence;
	}
	
	// static factory method
	public static List<FastaSequence> readFastaFile(String filePath) throws Exception
	{
		//generate a list to store header/sequence
		List<FastaSequence> list = new ArrayList<FastaSequence>();
		// read fasta file
		BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
		
		// check if the file is a fasta file
		//read the first line
		String firstLine = reader.readLine();
		String header;
		StringBuffer sequence = new StringBuffer();
		
		if(firstLine.startsWith(">"))
		{
			header= firstLine.substring(1);
			
		}else
		{
			//to avoid memory leak
			reader.close();
			throw new Exception("Please make sure this is a fasta file!");
		}
		
		
		for(String nextLine =reader.readLine();nextLine !=null; nextLine=reader.readLine())
		{
			//read from the second line
			// the program will implement else statement first
			
			if(nextLine.startsWith(">"))
            {
				//whenever reaches a new header, save the previous header/sequence
                    FastaSequence pair = new FastaSequence(header, sequence);
                    list.add(pair);
                    // update the header with new header
                    header=nextLine.substring(1);
                    // empty the sequence stringBuffer
                    sequence.setLength(0);

            }else
            {
            	//save 1-many lines of sequence into the stringBuffer
                    sequence.append(nextLine.trim());

            }



		}
		// generate a new FastaSequence to store the last header/sequence
		list.add(new FastaSequence(header,sequence));
		//close the reader
		reader.close();
		//return the list
		return list;

		
		
	}
	// returns the header of this sequence without the “>”
	public String getHeader()
	{	
		return header;
	}

	// returns the DNA sequence of this FastaSequence
	public String getSequence() 
	{ 
		return sequence.toString();
	}
		
	// returns the number of G’s and C’s divided by the length of this sequence
	public float getGCRatio()
	{ 
		int countGC = 0;
		
		String currentSequence = this.getSequence().toUpperCase();
		
		for( int x=0; x < currentSequence.length(); x++ )
		{
			char target = currentSequence.charAt(x);
			
			if( target=='C' || target=='G' )
				countGC++;
		}
		
		return (float)countGC / currentSequence.length();
	}

	public static void main(String[] args) throws Exception
	{
		// ask user for the absolute path of a fasta file
		System.out.println("Please type the absolute path of your fasta file");
		String filePath = System.console().readLine();

		List<FastaSequence> fastaList = FastaSequence.readFastaFile(filePath);

		for( FastaSequence fs : fastaList)
		{
		System.out.println(fs.getHeader());
		System.out.println(fs.getSequence());
		System.out.println(fs.getGCRatio());
		}

	}
}
