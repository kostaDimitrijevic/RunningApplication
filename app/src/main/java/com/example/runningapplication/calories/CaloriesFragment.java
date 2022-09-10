package com.example.runningapplication.calories;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.runningapplication.MainActivity;
import com.example.runningapplication.R;

import com.example.runningapplication.databinding.FragmentCaloriesBinding;
import com.example.runningapplication.threading.CustomLooperThread;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CaloriesFragment extends Fragment {

    private FragmentCaloriesBinding binding;
    private CaloriesViewModel caloriesViewModel;
    private NavController navController;
    private MainActivity mainActivity;
    private ExecutorService executorService;

    public CaloriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        caloriesViewModel = new ViewModelProvider(this).get(CaloriesViewModel.class);

       executorService = Executors.newFixedThreadPool(4);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCaloriesBinding.inflate(inflater, container, false);

        //caloriesViewModel.initByInstanceStateBundle(savedInstanceState);

        caloriesViewModel.getCaloriesBurned().observe(mainActivity, caloriesBurned -> {
                    if(caloriesBurned != -1){
                        String prefix = getResources().getString(R.string.calories_burned);
                        binding.burned.setText(prefix + ": " + caloriesBurned + " kcal");
                    }
                }
        );

        caloriesViewModel.getCaloriesNeeded().observe(mainActivity, caloriesNeeded -> {
            if(caloriesNeeded != -1){
                String prefix = getResources().getString(R.string.calories_needed);
                binding.need.setText(prefix + ": " + caloriesNeeded + " kcal");
            }
        });

        //spinner
        String[] metStrings = getResources().getStringArray(R.array.met_strings);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mainActivity, android.R.layout.simple_list_item_1, metStrings);
        binding.spinner.setAdapter(arrayAdapter);

        binding.calculate.setOnClickListener(v -> {

            try {
                double weight = fetchNumber(binding.weight).doubleValue();
                double height = fetchNumber(binding.height).doubleValue();
                int age = fetchNumber(binding.age).intValue();

                if (binding.radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(mainActivity, R.string.calories_error_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!binding.female.isChecked() && !binding.male.isChecked()) {
                    Toast.makeText(mainActivity, R.string.calories_error_message, Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isMale = binding.male.isChecked();

                double duration = fetchNumber(binding.duration).doubleValue();

                TypedArray metValues = getResources().obtainTypedArray(R.array.met_values);
                double met = metValues.getFloat(binding.spinner.getSelectedItemPosition(), 0);
                metValues.recycle();

                caloriesViewModel.updateValues(weight, height, age, isMale, duration, met);
            } catch (NumberFormatException | ParseException ignored) {
                //ignore
            }

            // dobijamo handler koji odgovara nasem lloooperu
            Handler uiThreadHandler = new Handler(Looper.getMainLooper());

            Future<Boolean> future = executorService.submit(() -> {
                // nesto
                SystemClock.sleep(1000);
                uiThreadHandler.post(() -> binding.calculate.setText("okay"));
                return true;
            });

            executorService.submit(() -> {
                try {
                    // vratice tip Future-a
                    Boolean retValue = future.get();

                    // toast je deo UI - ne treba da se postavlja kroz neku drugu nit
                    uiThreadHandler.post(() -> {
                        Toast.makeText(mainActivity, "finished " + retValue, Toast.LENGTH_SHORT).show();
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        return binding.getRoot();
    }

    private Number fetchNumber(TextInputLayout textInputLayout) throws ParseException {
        Number result = 0;
        try{
            result = NumberFormat.getInstance().parse(textInputLayout.getEditText().getText().toString());
        } catch (NumberFormatException | ParseException ex){
            Toast.makeText(mainActivity, R.string.calories_error_message, Toast.LENGTH_SHORT).show();
            textInputLayout.getEditText().requestFocus();
            throw ex;
        }

        return result;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if(caloriesViewModel.getCaloriesBurned().getValue() != null){
//            outState.putInt(CaloriesViewModel.CALORIES_BURNED_KEY, caloriesViewModel.getCaloriesBurned().getValue());
//        }
//        if(caloriesViewModel.getCaloriesNeeded().getValue() != null){
//            outState.putInt(CaloriesViewModel.CALORIES_NEEDED_KEY, caloriesViewModel.getCaloriesNeeded().getValue());
//        }
//    }
}