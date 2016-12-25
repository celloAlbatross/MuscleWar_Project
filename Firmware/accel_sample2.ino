//const int xpin = 0;                  // x-axis of the accelerometer
const int ypin = 2;// y-axis
const int ypin2 = 3;
//const int zpin = 4;                  // z-axis
void setup()
{
 // initialize the serial communications:
 Serial.begin(9600);
}
void loop()
{
 //int x = analogRead(xpin);  //read from xpin
 
 int y = analogRead(ypin);  //read from ypin
 int y2 = analogRead(ypin2);
 
 //int z = analogRead(zpin);  //read from zpin
 
float zero_G = 512.0; //ADC is 0~1023  the zero g output equal to Vs/2
                      //ADXL335 power supply by Vs 3.3V
float scale = 102.3;  //ADXL335330 Sensitivity is 330mv/g
                       //330 * 1024/3.3/1000  
float accelY1 = ((float)y - 329.5)/68.5*9.8;
float accelY2 = ((float)y2 - 329.5)/68.5*9.8;

Serial.print("Accel-Y: ");
Serial.print(accelY1);  //print y value on serial monitor
Serial.print(" ");
Serial.print(accelY2);
Serial.print("\n");
delay(100);
}



