package com.upiiz.dm_degr_05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.upiiz.dm_degr_05.util.basicas;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //1. Declarar Variables para interactuar con las vistas
    TextView tvDisplay;
    Button btnSiete, btnOcho, btnNueve, btnMas;
    Button btnCuatro, btnCinco, btnSeis, btnMenos;
    Button btnUno, btnDos, btnTres, btnDivision;
    Button btnCero, btnPunto, btnIgual, btnMulti;
    Button btnPorcentaje, btnParentesis1, btnParentesis2, btnC;

    //2. Variables de estado de la calculadora
    private int parentesisAbiertos = 0;
    private boolean resultadoMostrado = false;

    // Variables usadas durante la evaluación de la expresión
    private String exprEval;
    private int posEval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //3. Enlazar variables con vistas
        tvDisplay = findViewById(R.id.tvDisplay);
        btnSiete = findViewById(R.id.btnSiete);
        btnOcho = findViewById(R.id.btnOcho);
        btnNueve = findViewById(R.id.btnNueve);
        btnMas = findViewById(R.id.btnMas);
        btnCuatro = findViewById(R.id.btnCuatro);
        btnCinco = findViewById(R.id.btnCinco);
        btnSeis = findViewById(R.id.btnSeis);
        btnMenos = findViewById(R.id.btnMenos);
        btnUno = findViewById(R.id.btnUno);
        btnDos = findViewById(R.id.btnDos);
        btnTres = findViewById(R.id.btnTres);
        btnDivision = findViewById(R.id.btnDivision);
        btnCero = findViewById(R.id.btnCero);
        btnPunto = findViewById(R.id.btnPunto);
        btnIgual = findViewById(R.id.btnIgual);
        btnMulti = findViewById(R.id.btnMulti);
        btnPorcentaje = findViewById(R.id.btnPorcentaje);
        btnParentesis1 = findViewById(R.id.btnParentesis1);
        btnParentesis2 = findViewById(R.id.btnParentesis2);
        btnC = findViewById(R.id.btnC);

        //4. Escuchar los clics de los 20 botones
        btnSiete.setOnClickListener(this);
        btnOcho.setOnClickListener(this);
        btnNueve.setOnClickListener(this);
        btnMas.setOnClickListener(this);
        btnCuatro.setOnClickListener(this);
        btnCinco.setOnClickListener(this);
        btnSeis.setOnClickListener(this);
        btnMenos.setOnClickListener(this);
        btnUno.setOnClickListener(this);
        btnDos.setOnClickListener(this);
        btnTres.setOnClickListener(this);
        btnDivision.setOnClickListener(this);
        btnCero.setOnClickListener(this);
        btnPunto.setOnClickListener(this);
        btnIgual.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnPorcentaje.setOnClickListener(this);
        btnParentesis1.setOnClickListener(this);
        btnParentesis2.setOnClickListener(this);
        btnC.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnSiete) {
            agregarDigito("7");
        } else if (id == R.id.btnOcho) {
            agregarDigito("8");
        } else if (id == R.id.btnNueve) {
            agregarDigito("9");
        } else if (id == R.id.btnCuatro) {
            agregarDigito("4");
        } else if (id == R.id.btnCinco) {
            agregarDigito("5");
        } else if (id == R.id.btnSeis) {
            agregarDigito("6");
        } else if (id == R.id.btnUno) {
            agregarDigito("1");
        } else if (id == R.id.btnDos) {
            agregarDigito("2");
        } else if (id == R.id.btnTres) {
            agregarDigito("3");
        } else if (id == R.id.btnCero) {
            agregarDigito("0");
        } else if (id == R.id.btnPunto) {
            agregarPunto();
        } else if (id == R.id.btnMas) {
            procesarOperador("+");
        } else if (id == R.id.btnMenos) {
            procesarOperador("-");
        } else if (id == R.id.btnMulti) {
            procesarOperador("*");
        } else if (id == R.id.btnDivision) {
            procesarOperador("/");
        } else if (id == R.id.btnParentesis1) {
            agregarParentesisApertura();
        } else if (id == R.id.btnParentesis2) {
            agregarParentesisCierre();
        } else if (id == R.id.btnPorcentaje) {
            aplicarPorcentaje();
        } else if (id == R.id.btnC) {
            limpiarTodo();
        } else if (id == R.id.btnIgual) {
            calcularResultado();
        }
    }

    // Agrega un dígito concatenándolo a la expresión que se va armando
    private void agregarDigito(String digito) {
        String actual = tvDisplay.getText().toString();

        if (resultadoMostrado) {
            tvDisplay.setText(digito);
            resultadoMostrado = false;
            return;
        }

        if (actual.equals("0")) {
            tvDisplay.setText(digito);
        } else {
            tvDisplay.setText(actual + digito);
        }
    }

    // Evita que se agreguen dos puntos decimales al mismo número
    private void agregarPunto() {
        String actual = tvDisplay.getText().toString();

        if (resultadoMostrado) {
            tvDisplay.setText("0.");
            resultadoMostrado = false;
            return;
        }

        if (actual.isEmpty() || esOperador(actual.charAt(actual.length() - 1))
                || actual.charAt(actual.length() - 1) == '(') {
            tvDisplay.setText(actual + "0.");
            return;
        }

        String ultimoNumero = ultimoSegmentoNumerico(actual);
        if (!ultimoNumero.contains(".")) {
            tvDisplay.setText(actual + ".");
        }
    }

    // Se ejecuta al presionar un operador
    // Concatena el operador
    private void procesarOperador(String operador) {
        String actual = tvDisplay.getText().toString();

        if (actual.isEmpty()) {
            return;
        }
        resultadoMostrado = false;

        char ultimo = actual.charAt(actual.length() - 1);

        if (esOperador(ultimo)) {

            tvDisplay.setText(actual.substring(0, actual.length() - 1) + operador);
        } else if (ultimo == '(') {

            if (operador.equals("-")) {
                tvDisplay.setText(actual + operador);
            }
        } else {
            tvDisplay.setText(actual + operador);
        }
    }

    // Agrega un paréntesis de apertura. Si justo antes hay un número o un parentesis que cierra, se inserta una multiplicación
    private void agregarParentesisApertura() {
        String actual = tvDisplay.getText().toString();

        if (resultadoMostrado || actual.equals("0")) {
            tvDisplay.setText("(");
            resultadoMostrado = false;
        } else {
            char ultimo = actual.charAt(actual.length() - 1);
            if (Character.isDigit(ultimo) || ultimo == ')') {
                tvDisplay.setText(actual + "*(");
            } else {
                tvDisplay.setText(actual + "(");
            }
        }
        parentesisAbiertos++;
    }

    // Agrega un paréntesis de cierre solo si hay uno abierto pendiente y si tiene sentido cerrarlo ahi
    private void agregarParentesisCierre() {
        String actual = tvDisplay.getText().toString();

        if (parentesisAbiertos <= 0 || actual.isEmpty()) {
            return;
        }

        char ultimo = actual.charAt(actual.length() - 1);
        if (esOperador(ultimo) || ultimo == '(') {
            return;
        }

        tvDisplay.setText(actual + ")");
        parentesisAbiertos--;
    }

    //Para el porcentaje
    private void aplicarPorcentaje() {
        String actual = tvDisplay.getText().toString();

        Matcher matcher = Pattern.compile("(\\d+\\.?\\d*)$").matcher(actual);
        if (!matcher.find()) {
            return; // no hay un número al final sobre el cual aplicar %
        }

        double numero = Double.parseDouble(matcher.group(1));
        double porcentaje = numero / 100;

        String nuevoTexto = actual.substring(0, matcher.start()) + formatearNumero(porcentaje);
        tvDisplay.setText(nuevoTexto);
    }

    // Reinicia la calculadora
    private void limpiarTodo() {
        tvDisplay.setText("0");
        parentesisAbiertos = 0;
        resultadoMostrado = false;
    }

    // Se ejecuta al presionar el igual
    // Cierra automáticamente los paréntesis que hayan quedado abiertos,
    private void calcularResultado() {
        String expresion = tvDisplay.getText().toString();

        if (expresion.isEmpty() || esOperador(expresion.charAt(expresion.length() - 1))) {
            Toast.makeText(this, "Expresión incompleta", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder expresionCompleta = new StringBuilder(expresion);
        for (int i = 0; i < parentesisAbiertos; i++) {
            expresionCompleta.append(")");
        }

        try {
            exprEval = expresionCompleta.toString();
            posEval = 0;
            double resultado = evaluarSuma();

            tvDisplay.setText(formatearNumero(resultado));
            resultadoMostrado = true;
            parentesisAbiertos = 0;
        } catch (ArithmeticException e) {
            Toast.makeText(this, "No se puede dividir entre cero", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Expresión inválida", Toast.LENGTH_SHORT).show();
        }
    }


    private double evaluarSuma() {
        double resultado = evaluarProducto();

        while (posEval < exprEval.length()
                && (exprEval.charAt(posEval) == '+' || exprEval.charAt(posEval) == '-')) {
            char operador = exprEval.charAt(posEval);
            posEval++;
            double siguiente = evaluarProducto();
            resultado = (operador == '+')
                    ? basicas.suma(resultado, siguiente)
                    : basicas.resta(resultado, siguiente);
        }
        return resultado;
    }

    // Multiplicación y división se hace antes que la suma o resta
    private double evaluarProducto() {
        double resultado = evaluarFactor();

        while (posEval < exprEval.length()
                && (exprEval.charAt(posEval) == '*' || exprEval.charAt(posEval) == '/')) {
            char operador = exprEval.charAt(posEval);
            posEval++;
            double siguiente = evaluarFactor();
            resultado = (operador == '*')
                    ? basicas.multiplicacion(resultado, siguiente)
                    : basicas.division(resultado, siguiente);
        }
        return resultado;
    }

    private double evaluarFactor() {
        if (posEval < exprEval.length() && exprEval.charAt(posEval) == '(') {
            posEval++; // consume '('
            double resultado = evaluarSuma();
            if (posEval < exprEval.length() && exprEval.charAt(posEval) == ')') {
                posEval++; // consume ')'
            }
            return resultado;
        }

        if (posEval < exprEval.length() && exprEval.charAt(posEval) == '-') {
            posEval++;
            return -evaluarFactor();
        }

        if (posEval < exprEval.length() && exprEval.charAt(posEval) == '+') {
            posEval++;
            return evaluarFactor();
        }

        int inicio = posEval;
        while (posEval < exprEval.length()
                && (Character.isDigit(exprEval.charAt(posEval)) || exprEval.charAt(posEval) == '.')) {
            posEval++;
        }

        if (inicio == posEval) {
            throw new RuntimeException("Se esperaba un número");
        }

        return Double.parseDouble(exprEval.substring(inicio, posEval));
    }


    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Devuelve el número que se está escribiendo actualmente
    private String ultimoSegmentoNumerico(String expresion) {
        int i = expresion.length() - 1;
        while (i >= 0 && !esOperador(expresion.charAt(i)) && expresion.charAt(i) != '(') {
            i--;
        }
        return expresion.substring(i + 1);
    }


    private boolean esNumeroValido(String texto) {
        if (texto == null || texto.trim().isEmpty() || texto.equals(".")) {
            return false;
        }
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String formatearNumero(double numero) {
        if (!Double.isInfinite(numero) && !Double.isNaN(numero) && numero == Math.floor(numero)
                && Math.abs(numero) < 1_000_000_000L) {
            return String.valueOf((long) numero);
        }
        return String.valueOf(numero);
    }
}