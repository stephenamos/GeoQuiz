package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
	
	private boolean mAnswerShown;
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;

	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
		
		if (isAnswerShown) {
			mAnswerShown = true;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);

		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

		mAnswerTextView = (TextView) this.findViewById(R.id.answerTextView);

		mShowAnswerButton = (Button) this.findViewById(R.id.showAnswerButton);
		
		setAnswerShownResult(false);
		
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
			}
		});

		if (savedInstanceState != null) {
			setAnswerShownResult(savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN));
		}
		
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(CheatActivity.EXTRA_ANSWER_SHOWN, mAnswerShown);
	}

}
