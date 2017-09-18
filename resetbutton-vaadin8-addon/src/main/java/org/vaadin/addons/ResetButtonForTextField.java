package org.vaadin.addons;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.Registration;
import com.vaadin.ui.TextField;

import org.vaadin.addons.client.ResetButtonClickRpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResetButtonForTextField extends AbstractExtension {
    private final List<ResetButtonClickListener> listeners = new ArrayList<>();

    public static ResetButtonForTextField extend(final TextField field) {
        ResetButtonForTextField resetButton = new ResetButtonForTextField();
        resetButton.extend((AbstractClientConnector) field);
        resetButton.addResetButtonClickedListener(field::clear);
        return resetButton;
    }

    ResetButtonForTextField() {
        ResetButtonClickRpc resetButtonClickRpc = () -> listeners.forEach(ResetButtonClickListener::resetButtonClicked);
        registerRpc(resetButtonClickRpc);
    }

    public Registration addResetButtonClickedListener(ResetButtonClickListener listener) {
        Objects.requireNonNull(listener);

        listeners.add(listener);

        return () -> listeners.remove(listener);
    }
}
