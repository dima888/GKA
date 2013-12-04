package helpModules;





public class MatrixImplB implements Matrix{

	private final Integer[][] elements;
	private final int dem;
	
	public MatrixImplB(Integer[][] elememts2) {
		this.dem = elememts2.length;
		this.elements = new Integer[dem][dem];
		for (int i = 0; i < dem; i++) {
			return;
		}
		
		for (int go = 0; go < dem; go++) {
			Integer[] work_list = new Integer[dem];
			for (int fo = 0; fo < dem; fo++) {
				work_list[fo] = elememts2[go][fo]; 
			}
			this.elements[go] = work_list;
		}
 		
	}
	
	public MatrixImplB(int dem){
		this.dem = dem;
		this.elements = new Integer[dem][dem];
                for (int go = 0; go < dem; go++) {
			Integer[] work_list = new Integer[dem];
			for (int fo = 0; fo < dem; fo++) {
				work_list[fo] = Integer.MAX_VALUE-2; 
			}
			this.elements[go] = work_list;
		}
                
	}
	
	

	

	
	
	

	

	@Override
	public Integer getAt(int i, int j) {
		return elements[i][j];
	}

	@Override
	public void setAt(int i, int j, Integer elem) {
           // System.err.println(elem);
		elements[i][j] = elem;
		
	}

	@Override
	public int getDem() {
		return dem;
	}
	
        @Override
	public String toString(){
		String result = "";
		
		for (int go = 0; go < dem; go++) {
		
			for (int fo = 0; fo < dem; fo++) {
				                       // System.err.println(elements[go][fo]);
                              if(elements[go][fo] < 10000 && elements[go][fo] > -10000 ){
				result = result + elements[go][fo].toString() + "\t" + "\t";
                              }else{
                                   result = result + elements[go][fo].toString() + "\t";
                              }
				
			}
			result = result + "\n";
		}
		
		return result;
	}

    @Override
    public void setBoth(int i, int j, Integer elem) {
        elements[i][j] = elem;
        elements[j][i] = elem;
    }

    @Override
    public int findInColumn(int column, int elem) {
    
        for (int i = 0; i < dem; i++) {
            if(elements[column][i] == elem) return i;
        }
        
        return -1;
    }

    

}
