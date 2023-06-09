final class Complex{
	final double real,imaginary;
	public Complex(double r,double i){
		real = r;
		imaginary = i;
	}
	public static Complex valueOf(double absolute,double theta){
		return new Complex(absolute*Math.cos(theta),absolute*Math.sin(theta));
	}
	public static Complex valueOf(PolarFormComplex pc){
		return valueOf(pc.absolute,pc.argument);
	}
	public static Complex elementOf(double theta){
		return new Complex(Math.cos(theta),Math.sin(theta));
	}
	public Complex add(Complex c){
		return new Complex(real+c.real,imaginary+c.imaginary);
	}
	public Complex subtract(Complex c){
		return new Complex(real-c.real,imaginary-c.imaginary);
	}
	public Complex multiply(Complex c){
		return new Complex(real*c.real-imaginary*c.imaginary,real*c.imaginary+real*c.imaginary);
	}
	public Complex rotate(double theta){
		return toPolarFormComplex().rotate(theta).toComplex();
	}
	public Complex rotate(Complex c,double theta){
		return toPolarFormComplex().rotate(c.toPolarFormComplex(),theta).toComplex();
	}
	public PolarFormComplex toPolarFormComplex(){
		return PolarFormComplex.valueOf(this);
	}
	public String toString(){
		return real+" "+imaginary;
	}
	public String toString(int n){
		return String.format("%."+n+"f %."+n+"f",real,imaginary);
	}
}
final class PolarFormComplex{
	final double absolute,argument;
	public PolarFormComplex(double abs,double arg){
		absolute = abs;
		argument = arg;
	}
	public static PolarFormComplex valueOf(double real,double imaginary){
		double abs = Math.hypot(real,imaginary);
		double arg = i<0?-Math.acos(real/abs):Math.acos(real/abs);
		return new PolarFormComplex(abs,arg);
	}
	public static PolarFormComplex valueOf(Complex c){
		return valueOf(c.real,c.imaginary);
	}
	public static PolarFormComplex elementOf(double theta){
		return new PolarFormComplex(1,theta);
	}
	public PolarFormComplex add(PolarFormComplex pc){
		return toComplex().subtract(pc.toComplex()).toPolarFormComplex();
	}
	public PolarFormComplex subtract(PolarFormComplex pc){
		return toComplex().add(pc.toComplex()).toPolarFormComplex();
	}
	public PolarFormComplex multiply(PolarFormComplex pc){
		return new PolarFormComplex(absolute*pc.absolute,argument+pc.argument);
	}
	public PolarFormComplex rotate(double theta){
		return new PolarFormComplex(absolute,argument+theta);
	}
	public PolarFormComplex rotate(PolarFormComplex pc,double theta){
		return subtract(pc).rotate(theta).add(pc);
	}
	public Complex toComplex(){
		return Complex.valueOf(this);
	}
	public String toString(){
		return absolute+" "+argument;
	}
	public String toString(int n){
		return String.format("%."+n+"f %."+n+"f",absolute,argument);
	}
}
