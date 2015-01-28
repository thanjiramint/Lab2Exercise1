package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void updateAnsDisplay(String s) {
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(s);
    }

    public void recalculate() {
        //Calculate the expression and display the output
        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");

        int result =0;
        String op = "+";
        for(int  i=0;i<tokens.length;i++)
        {
            if(i==0)
            {
                result=Integer.parseInt(tokens[0]);
            }
            if(isOperand(tokens[i].charAt(0))){
                op = tokens[i];

            }
            if (!isOperand(tokens[i].charAt(0)) && (i > 0)){
                int var = Integer.parseInt(tokens[i]);
                if(op.equals("+"))
                    result=result+var;
                else if(op.equals("-"))
                    result=result-var;
                else if(op.equals("*"))
                    result=result*var;
                else if(op.equals("/"))
                    result=result/var;

                //expr.append(result+"");
                updateAnsDisplay(Integer.toString(result));
            }

        }

    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void operatorClicked(View v) {

    //IF the last character in expr is not an operator and expr is not "",
    //THEN append the clicked operator and updateExprDisplay,
    //ELSE do nothing
        String c = ((TextView)v).getText().toString();
     if (!expr.toString().isEmpty() && !isOperand(expr.charAt(expr.length()-1)))
        {
            expr.append(c);
            updateExprDisplay();
        }
}
    private boolean isOperand(char c){
        return c == '/' || c == '*' || c == '-' || c == '+';
    }


    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();
        updateAnsDisplay("");
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
    }

    public void equalClicked(View v) {
        TextView tvans = (TextView)findViewById(R.id.tvAns);
        TextView tvexpr = (TextView)findViewById(R.id.tvExpr);

        tvexpr.setText(tvans.getText().toString());
        tvans.setText(tvans.getText().toString());

    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            updateExprDisplay();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    int mem = 0;
    public void mClicked(View v){
           int mb = v.getId();
            if(mb==R.id.madd)
            {
                TextView tvAns = (TextView)findViewById(R.id.tvAns);
                mem = mem+ Integer.parseInt(tvAns.getText().toString());
                Toast t = Toast.makeText(this.getApplicationContext(), "Memory is "+mem, Toast.LENGTH_SHORT);
                t.show();
            }

        if(mb==R.id.msub)
        {
            TextView tvAns = (TextView)findViewById(R.id.tvAns);
            mem =mem- Integer.parseInt(tvAns.getText().toString());
            Toast t = Toast.makeText(this.getApplicationContext(), "Memory is "+mem, Toast.LENGTH_SHORT);
            t.show();
        }

        if(mb==R.id.mr)
        {
            TextView tvans = (TextView)findViewById(R.id.tvAns);
            TextView tvexpr = (TextView)findViewById(R.id.tvExpr);

            tvexpr.setText(Integer.toString(mem));
            tvans.setText(Integer.toString(mem));
        }
        else if(mb== R.id.mc) {
                TextView tvAns = (TextView)findViewById(R.id.tvAns);
                mem =0;
                Toast t = Toast.makeText(this.getApplicationContext(),"Memory is "+mem,Toast.LENGTH_SHORT);
                t.show();

            }
    }
}
