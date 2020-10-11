package th.ac.su.cp.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import th.ac.su.cp.quizgame.model.WordItem;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mQuestionImageView;
    private TextView text;
    private int score,count;
    private Button[] mButtons =  new Button [4];
    private String mAnswerWord;
    private  Random mRandom;
    private List<WordItem> mItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        text = findViewById(R.id.score_text);


        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choice_1_button);
        mButtons[1] = findViewById(R.id.choice_2_button);
        mButtons[2] = findViewById(R.id.choice_3_button);
        mButtons[3] = findViewById(R.id.choice_4_button);

        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        mRandom = new Random();
        newQuiz();

    }

    private void newQuiz() {
        text.setText(String.valueOf(score)+" score");
        mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        //สุ่ม Index ของคำตอบ (คำถาม)
        int answerIndex = mRandom.nextInt(mItemList.size());
        //เข้าถึง WordItem ตาม index ที่สุ่มได้
        WordItem item = mItemList.get(answerIndex);

        mQuestionImageView.setImageResource(item.imageResId);

        //เก็บคำตอบไว้ตรวจ
        mAnswerWord = item.word;

        //สุ่มตำแหน่งปุ่มที่จะแสดงคำตอบ
        int randomButton = mRandom.nextInt(4);
        mButtons[randomButton].setText(item.word);
        //ลบ WordItem ที่เป็นคำตอบจาด list
        mItemList.remove(item);

        //เอา list ที่เหลือมา  suffle
        Collections.shuffle(mItemList);

        //เอาข้อมูลจาก list แสดงที่ปุ่ม 3 ปุ่ม ที่ไม่ใช่คำตอบ
        for(int i = 0;i<4;i++){
            if(i == randomButton){
                continue;
            }
            mButtons[i].setText(mItemList.get(i).word);
        }
    }

    @Override
    public void onClick(View view) {
        Button b = findViewById(view.getId());
        String buttonText = b.getText().toString();

        if(buttonText.equals(mAnswerWord)){
            Toast.makeText(GameActivity.this,"ถูกต้องครับ",Toast.LENGTH_SHORT).show();
            score++;
            text.setText(String.valueOf(score)+" score");
        }else {
            Toast.makeText(GameActivity.this, "ผิดครับ", Toast.LENGTH_SHORT).show();
            text.setText(String.valueOf(score) + " score");
        }
        count++;
        if(count<5)
            newQuiz();
        else{
            count = 0;
            AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
            dialog.setTitle("สรุปผล");
            dialog.setMessage("คุณได้ "+String.valueOf(score)+" คะแนน"+"\n\n"+"ต้องการเล่นเกมใหม่หรือไม่");
            dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    score = 0;
                    newQuiz();
                }
            });
            dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.show();
        }
    }
}