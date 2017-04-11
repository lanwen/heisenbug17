package ru.lanwen.heisenbug;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.lanwen.heisenbug.WiremockResolver.WIREMOCK_PORT;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class WiremockAddressResolver implements ParameterResolver {
    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().isAnnotationPresent(Uri.class)
                && String.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext context) {
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(WiremockResolver.class));

        return "http://localhost:" + store.get(WIREMOCK_PORT);
    }

    /**
     * To target host:port injection
     */
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Uri {
    }
}
