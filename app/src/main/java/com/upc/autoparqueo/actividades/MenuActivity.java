package com.upc.autoparqueo.actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.upc.autoparqueo.R;
import com.upc.autoparqueo.modelos.Usuario;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public Usuario usuario;

    private View header;
    private ImageView imgUsuario;
    private TextView tvNombreUsuario;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);

        imgUsuario = header.findViewById(R.id.imageView);
        tvNombreUsuario = header.findViewById(R.id.tvNombreUsuario);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_buscar_estacionamiento,
                R.id.nav_consultar_estacionamiento,
                R.id.nav_registrar_estacionamiento,
                R.id.nav_consultar_reserva)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.v("destination"," -> "+destination.getLabel());
            }
        });


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        ImageView imagen = headerView.findViewById(R.id.imageView);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ActualizarUsuarioActivity.class);
                intent.putExtra("KEY_USUARIO", usuario);
                startActivity(intent);
            }
        });

        navigationView.getMenu().clear();
        usuario = getIntent().getExtras().getParcelable("KEY_USUARIO");

        if(usuario.isAdministrador()) {
          navigationView.inflateMenu(R.menu.activity_menu_drawer_administrador);
        } else {
            navigationView.inflateMenu(R.menu.activity_menu_drawer);
        }


        MenuItem cerrarSesionItem = navigationView.getMenu().findItem(R.id.nav_cerrar_sesion);
        cerrarSesionItem.setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(MenuActivity.this, InicioSesionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        });


        Bitmap bitmap = BitmapFactory.decodeByteArray(usuario.getFoto(), 0, usuario.getFoto().length);
        imgUsuario.setImageBitmap(bitmap);

        tvNombreUsuario.setText((usuario.getNombre() == null || usuario.getNombre().isEmpty())? usuario.getCodigo() : usuario.getNombre());
    }

    public void redirectConsulta(){
        navController.popBackStack(R.id.nav_consultar_estacionamiento, true);
        navController.navigate(R.id.nav_consultar_estacionamiento);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




}
