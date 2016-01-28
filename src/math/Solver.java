package math;

public class Solver {

	Var var;
	Definition[] defs;
	Var[] knowns;

	public Solver(Var v, Definition[] d, Var[] n){
		v = var;
		int x = 0;
		for(int i = 0; i<d.length;i++){
			if(d[i].name.equals(var)){
				x++;
			}
		}
		defs = new Definition[x];
		x = 0;
		for(int i = 0; i<d.length;i++){
			if(d[i].name.equals(var)){
				defs[x] = d[i];
				x++;
			}
		}

	}
}
