package com.vayama.businessservice.bitcoinservice;

import java.io.Serializable;

import com.vayama.jsonobject.Button;

public class Request implements Serializable {

	private static final long serialVersionUID = 3130834102229546418L;
	private Button _button;

	public Button getButton() {
		return _button;
	}

	public void setButton(Button button) {
		_button = button;
	}
}
