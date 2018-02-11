//Jianing Sun & Tianlin Xu

package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A heap file stores a collection of tuples. It is also responsible for managing pages.
 * It needs to be able to manage page creation as well as correctly manipulating pages
 * when tuples are added or deleted.
 * @author Sam Madden modified by Doug Shook
 *
 */
public class HeapFile {
	
	public static final int PAGE_SIZE = 4096;
    private File file;
    private TupleDesc tupleDesc;
	
	/**
	 * Creates a new heap file in the given location that can accept tuples of the given type
	 * @param f location of the heap file
	 * @param types type of tuples contained in the file
	 */
	public HeapFile(File f, TupleDesc type) {
		//your code here
		this.file = f;
		this.tupleDesc = type;
	}
	
	public File getFile() {
		//your code here
		return file;
	}
	
	public TupleDesc getTupleDesc() {
		//your code here
		return tupleDesc;
	}
	
	/**
	 * Creates a HeapPage object representing the page at the given page number.
	 * Because it will be necessary to arbitrarily move around the file, a RandomAccessFile object
	 * should be used here.
	 * @param id the page number to be retrieved
	 * @return a HeapPage at the given page number
	 * @throws IOException 
	 */

	public HeapPage readPage(int id) {
		//your code here
		byte[] page = new byte[PAGE_SIZE];
		RandomAccessFile getpage = null;
		
		try {
			getpage = new RandomAccessFile(file, "r");
			getpage.seek(PAGE_SIZE * id);
			getpage.read(page, 0, PAGE_SIZE);
			getpage.close();
			return new HeapPage(id, page, getId());
			} catch (IOException e) 
		{e.printStackTrace();
			  }
		return null;
		}
		
	

	

	/**
	 * Returns a unique id number for this heap file. Consider using
	 * the hash of the File itself.
	 * @return
	 */
	public int getId() {
		//your code here
		return file.getAbsoluteFile().hashCode();
	}
	
	/**
	 * Writes the given HeapPage to disk. Because of the need to seek through the file,
	 * a RandomAccessFile object should be used in this method.
	 * @param p the page to write to disk
	 * @throws IOException 
	 */
	public void writePage(HeapPage p){
		//your code here
		RandomAccessFile page = null;
		try {
			page = new RandomAccessFile(file, "rw");
			page.seek(p.getId() * PAGE_SIZE);
			page.write(p.getPageData(), 0 , PAGE_SIZE);
			page.close();
		}catch (IOException e)
		{e.printStackTrace();}		

	}
	
	/**
	 * Adds a tuple. This method must first find a page with an open slot, creating a new page
	 * if all others are full. It then passes the tuple to this page to be stored. It then writes
	 * the page to disk (see writePage)
	 * @param t The tuple to be stored
	 * @return The HeapPage that contains the tuple
	 * @throws Exception 
	 */
	public HeapPage addTuple(Tuple t) throws Exception {
		//your code here
		HeapPage heappage = null;
		
		//find a page with open slot
		for (int i = 0; i < this.getNumPages(); i++) {
			HeapPage page = this.readPage(i);
			for (int s = 0; s < page.getNumSlots(); i++) {
				if (page.hasEmptySlot()) {
					heappage = page;
					break;
				}
			}
		}
		
		//add tuple
		heappage.addTuple(t);
		this.writePage(heappage);
		return heappage;
	}
	
	/**
	 * This method will examine the tuple to find out where it is stored, then delete it
	 * from the proper HeapPage. It then writes the modified page to disk.
	 * @param t the Tuple to be deleted
	 * @throws Exception 
	 */
	public void deleteTuple(Tuple t) throws Exception{
		//your code here
		HeapPage page = this.readPage(t.getId());
		page.deleteTuple(t);
		this.writePage(page);
	}
	
	
	/**
	 * Returns an ArrayList containing all of the tuples in this HeapFile. It must
	 * access each HeapPage to do this (see iterator() in HeapPage)
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<Tuple> getAllTuples() throws IOException {
		//your code here
		ArrayList<Tuple> pagelist = new ArrayList<Tuple>();
		for (int i = 0; i < this.getNumPages(); i++) {
			HeapPage page = this.readPage(i);
			Iterator<Tuple> tuple = page.iterator();
			while(tuple.hasNext()) {
				pagelist.add(tuple.next());
			}
		}
		return pagelist;
	}
	
	/**
	 * Computes and returns the total number of pages contained in this HeapFile
	 * @return the number of pages
	 */
	public int getNumPages() {
		int page = (int) Math.ceil(file.length()/PAGE_SIZE);
		return page;
	}
}
