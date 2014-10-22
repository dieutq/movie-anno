/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package movieannot.BasicModel;

/**
 *
 * @author dieutq
 */
import java.util.LinkedList;
import java.util.List;

public class SearchResults {
    public List<String> list;
	public int relevantResults;
	
	public SearchResults() {
		this.list = new LinkedList<String>();
		this.relevantResults = 0;
	}
}
