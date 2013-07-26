package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	//	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mPreviousButton;
	private Button mCheatButton;

	private TextView mQuestionTextView;

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_oceans, true),
	};

	private int mCurrentIndex = 0;

	private boolean mIsCheater;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data == null) {
			return;
		}
		
		if (!mIsCheater) {
			mIsCheater = (Boolean) data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);	
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		Log.d(TAG, "onCreate(Bundle) called.");
		setContentView(R.layout.activity_quiz);

		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex = ++mCurrentIndex % mQuestionBank.length;
				updateQuestion();
			}
		});

		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex = ++mCurrentIndex % mQuestionBank.length;
				updateQuestion();
			}
		});

		mPreviousButton = (Button) findViewById(R.id.previous_button);
		mPreviousButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex--;
				if (mCurrentIndex == -1) { mCurrentIndex = mQuestionBank.length - 1; }
				updateQuestion();
			}
		});

		if(savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			mIsCheater = savedInstanceState.getBoolean(CheatActivity.EXTRA_ANSWER_SHOWN);
		}

		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i, 0); //The second parameter is the request code. The request code
				//is a user-defined integer that is sent to the child activity and then received back by the parent.
				//It is used when an activity starts more than one type of child activity and needs to tell who is reporting back.
			}
		});

		updateQuestion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

	private void updateQuestion() {
		try {

			int question = mQuestionBank[mCurrentIndex].getQuestion();
			mQuestionTextView.setText(question);	
		} catch (ArrayIndexOutOfBoundsException e) {
			//			Log.e(TAG, "Index was out of bounds.", e);
		}
	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

		int messageResId = 0;

		if (mIsCheater) {
			messageResId = R.string.judgement_toast;
		} else {
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}

		Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
	}


	/**
	 * The default implementation of onSaveInstanceState(...) asks all of the activity's views(The UI widgets) to save their
	 * state as data in the Bundle object. Note: All custom classes that wish to save data to the bundle must be Serializable
	 * 
	 * @param outState A Bundle is a structure that maps string keys to values of certain limited types.
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//		Log.i(TAG, "onSaveInstanceState() called.");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
		savedInstanceState.putBoolean(CheatActivity.EXTRA_ANSWER_SHOWN, mIsCheater);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//		Log.d(TAG, "onDestroy() method called.");
	}

	@Override
	protected void onPause() {
		super.onPause();
		//		Log.d(TAG, "onPause() method called.");
	}

	@Override
	protected void onResume() {
		super.onResume();
		//		Log.d(TAG, "onResume() method called.");
	}

	@Override
	protected void onStart() {
		super.onStart();
		//		Log.d(TAG, "onStart() method called.");
	}

	@Override
	protected void onStop() {
		super.onStop();
		//		Log.d(TAG, "onStop() method called.");
	}

}
