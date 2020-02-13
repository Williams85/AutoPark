package com.upc.autoparqueo.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upc.autoparqueo.R;
import com.upc.autoparqueo.modelos.Estacionamiento;
import com.upc.autoparqueo.modelos.Usuario;
import com.upc.autoparqueo.service.ApiClient;
import com.upc.autoparqueo.service.ResponseGeneric;
import com.upc.autoparqueo.service.request.RequestReservaEstacionamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservarEstacionamientoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button btnReservar;
    private GoogleMap mMap;

    private TextView tvHoraInicio, tvHoraFin, tvFecha,tvTotalHora, tvHorarioAtencion;
    private TextView tvNombre, tvDireccion, tvDistrito, tvPrecioHora, tvMontoTotal;

    Estacionamiento estacionamiento;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_estacionamiento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnReservar = findViewById(R.id.btnReservar);
        tvNombre = findViewById(R.id.tvNombre);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvDistrito = findViewById(R.id.tvDistrito);
        tvPrecioHora = findViewById(R.id.etPrecioHora);
        tvMontoTotal = findViewById(R.id.tvMontoTotal);
        tvHorarioAtencion = findViewById(R.id.tvHorarioAtencion);
        tvFecha = findViewById(R.id.tvFecha);
        tvHoraInicio = findViewById(R.id.tvHorarioInicio);
        tvHoraFin = findViewById(R.id.tvHorarioFin);
        tvTotalHora = findViewById(R.id.tvTotalHora);


        estacionamiento = getIntent().getExtras().getParcelable("KEY_ESTACIONAMIENTO");
        usuario = getIntent().getExtras().getParcelable("KEY_USUARIO");

        tvNombre.setText("Nombre: "+estacionamiento.getNombre());
        tvDireccion.setText("Direccion: "+estacionamiento.getDireccion());
        tvDistrito.setText("Distrito: "+estacionamiento.getNomDistrito());
        tvPrecioHora.setText(""+estacionamiento.getPrecio());
        tvHorarioAtencion.setText("Horario de atención: "+estacionamiento.getHoraInicio()+" a "+estacionamiento.getHoraFin());

        tvTotalHora.setText("0");

        btnReservar.setOnClickListener(view -> reservarEstacionamiento());

        tvHoraInicio.setText(estacionamiento.getHoraInicio());
        tvHoraFin.setText(estacionamiento.getHoraInicio());
        tvFecha.setText(formatDate(c.getTime()));
        fechaInicioLocal = convertStringtoTotime(tvFecha.getText().toString()+" "+estacionamiento.getHoraInicio());
        fechaFinLocal = convertStringtoTotime(tvFecha.getText().toString()+" "+estacionamiento.getHoraFin());




        setHoras();

        tvFecha.setOnClickListener(view -> {
           obtenerFecha((TextView)view);
        });
        tvHoraInicio.setOnClickListener(view -> {


            obtenerHora(fechaInicio.getHours(),fechaInicio.getMinutes(),(TextView) view);
        });
        tvHoraFin.setOnClickListener(view -> {

            obtenerHora(fechaFin.getHours(),fechaFin.getMinutes(),(TextView) view);

        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void calcularTotal() {
        double totalHora;

        try {
            totalHora = Double.parseDouble(tvTotalHora.getText().toString());
        }catch (Exception ex){
            totalHora = 0.0;
        }
        montoTotal = estacionamiento.getPrecio() * totalHora;


        tvMontoTotal.setText("S/ "+montoTotal);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(estacionamiento.getLatitud(), estacionamiento.getLongitud());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Estacionamiento: "+estacionamiento.getNombre()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f));
    }
    double montoTotal = 0.0;

    public void reservarEstacionamiento(){

        showProgress("Reservando estacionamiento...");

        RequestReservaEstacionamiento request = new RequestReservaEstacionamiento();
        request.setCodEstacionamiento(estacionamiento.getId());
        request.setCodUsuario(usuario.getCodUsuario());
        request.setFechaReserva(tvFecha.getText().toString());
        request.setHoraInicio(tvHoraInicio.getText().toString());
        request.setHoraFin(tvHoraFin.getText().toString());
        request.setHorasReserva(tvTotalHora.getText().toString().isEmpty() ? 0 : Double.parseDouble(tvTotalHora.getText().toString()));
        request.setMonto(montoTotal);


        ApiClient.getInstance().reservarEstacionamiento(request, new Callback<ResponseGeneric<Boolean>>() {
            @Override
            public void onResponse(Call<ResponseGeneric<Boolean>> call, Response<ResponseGeneric<Boolean>> response) {
                hideProgress();
                if (response.isSuccessful()) {

                    ResponseGeneric<Boolean> data = response.body();
                    if(data.getValor()){
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGeneric<Boolean>> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    public final Calendar c = Calendar.getInstance();
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int mes = c.get(Calendar.MONTH);
    final int anio = c.get(Calendar.YEAR);

    private void obtenerHora(int hourOfDayInput,int minuteInput, TextView tvView){
        TimePickerDialog recogerHora = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
            String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            //String hora = horaFormateada + ":" + minutoFormateado + " " + AM_PM;
            String strHora = horaFormateada + ":" + minutoFormateado + " " + AM_PM;
            String strHoraFormat = formatHour(strHora);
            String hora = hourOfDay + ":" + minute;
                if(tvView == tvHoraInicio){
                    fechaInicioTemp = convertStringtoTotimev2(tvFecha.getText().toString()+" "+hora);
                    if(fechaInicioLocal.compareTo(fechaInicioTemp) > 0 || fechaInicioTemp.compareTo(fechaFinLocal) > 0 ){
                        openDialog("La hora "+strHoraFormat+" no esta en el horario de atención.", (dialogInterface, i) -> {
                            obtenerHora(fechaInicio.getHours(),fechaInicio.getMinutes(),tvHoraInicio);
                        });
                    } else {
                        tvView.setText(strHoraFormat);
                        setHoras();
                    }
                }
                else if(tvView == tvHoraFin){
                    fechaFinTemp = convertStringtoTotimev2(tvFecha.getText().toString()+" "+hora);
                    if(fechaFinTemp.compareTo(fechaFinLocal) > 0 || fechaInicioLocal.compareTo(fechaFinTemp) > 0){
                        openDialog("La hora "+strHoraFormat+" no esta en el horario de atención.", (dialogInterface, i) -> {
                            obtenerHora(fechaFin.getHours(),fechaFin.getMinutes(),tvHoraFin);
                        });
                    } else {
                        tvView.setText(strHoraFormat);
                        setHoras();
                    }
                }




        }, hourOfDayInput, minuteInput, false);

        recogerHora.setCancelable(false);
        recogerHora.show();
    }

    private void obtenerFecha(TextView tvView){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year =  datePicker.getYear();

                c.set(year, month, day);

                String fecha = formatDate(c.getTime());
                tvView.setText(fecha);
                fechaInicioLocal = convertStringtoTotime(fecha+" "+estacionamiento.getHoraInicio());
                fechaFinLocal = convertStringtoTotime(fecha+" "+estacionamiento.getHoraFin());

                setHoras();

            }
        },anio,mes,dia);

                recogerFecha.show();
    }


    private Date fechaInicioLocal = null;
    private Date fechaFinLocal = null;
    private Date fechaInicio = null;
    private Date fechaFin = null;

    private Date fechaInicioTemp = null;
    private Date fechaFinTemp = null;

    public void setHoras(){
        fechaInicio = convertStringtoTotime(tvFecha.getText().toString()+" "+tvHoraInicio.getText().toString());
        fechaFin = convertStringtoTotime(tvFecha.getText().toString()+" "+tvHoraFin.getText().toString());
        obtenerCantidadHora();
        calcularTotal();
    }

    public String formatDate(Date date){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
    public String formatHour(Date date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public String formatHour(String hora){

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat1.format(date);
    }

    public Date convertStringtoTotime(String strDate){


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa", Locale.ENGLISH);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        Date date1 = null;
        try {
            date = dateFormat.parse(strDate);
            String strDate1 = dateFormat1.format(date);
            date1 = dateFormat1.parse(strDate1);

        } catch (ParseException e) {
        }

        return date;
    }

    public Date convertStringtoTotimev2(String strDate){


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:m", Locale.ENGLISH);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        Date date1 = null;
        try {
            date = dateFormat.parse(strDate);
            String strDate1 = dateFormat1.format(date);
            date1 = dateFormat1.parse(strDate1);

        } catch (ParseException e) {
        }

        return date;
    }



    public boolean fechaValida(){

        return true;
    }
    public void obtenerCantidadHora(){
        double diff = fechaFin.getTime() - fechaInicio.getTime();
        double seconds = diff / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;

        tvTotalHora.setText(""+hours);
        double days = hours / 24;
    }

    private ProgressDialog progress;
    public void showProgress(String msg){
        progress = new ProgressDialog(this);
        progress.setMessage(msg);
        progress.show();
    }

    public void hideProgress(){
        if (progress != null){
            progress.dismiss();
            progress = null;
        }
    }

    public void openDialog(String msg, DialogInterface.OnClickListener listener){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta!");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", listener);

        builder.show();
    }
}


