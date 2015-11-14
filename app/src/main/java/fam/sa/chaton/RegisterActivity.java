package fam.sa.chaton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import fam.sa.chaton.custom.BaseActivity;
import fam.sa.chaton.utils.Utils;

/**
 * @author Faheem
 * The Class RegisterActivity is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this ChatActivity app.
 */
public class RegisterActivity extends BaseActivity
{

	/** The username EditText. */
	private EditText user;

	/** The password EditText. */
	private EditText pwd;

	/** The email EditText. */
	private EditText email;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		email = (EditText) findViewById(R.id.email);
	}


	@Override
	public void onClick(View v)
	{
		super.onClick(v);

		String u = user.getText().toString();
		String p = pwd.getText().toString();
		String e = email.getText().toString();
		if (u.length() == 0 || p.length() == 0 || e.length() == 0)
		{
			Utils.showDialog(this, R.string.err_fields_empty);
			return;
		}
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_wait));

		final ParseUser pu = new ParseUser();
		pu.setEmail(e);
		pu.setPassword(p);
		pu.setUsername(u);
		pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e)
			{
				dia.dismiss();
				if (e == null)
				{
					UserListActivity.user = pu;
					startActivity(new Intent(RegisterActivity.this, UserListActivity.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
					Utils.showDialog(
							RegisterActivity.this,
							getString(R.string.err_singup) + " "
									+ e.getMessage());
					e.printStackTrace();
				}
			}
		});

	}
}
