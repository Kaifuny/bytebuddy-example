import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import java.lang.instrument.Instrumentation;

/**
 * Created by sbiger on 2017-05-05.
 */
public class TimeAgent {
    public static void premain(String arguments,
                               Instrumentation instrumentation) {
        System.out.println("start premain "+arguments);
        new AgentBuilder.Default()
                .type(ElementMatchers.nameEndsWith("Timed"))
                .transform((builder, type, classLoader, module) ->
                        builder.method(ElementMatchers.any())
                                .intercept(MethodDelegation.to(TimingInterceptor.class))
                )
                //.with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .installOn(instrumentation);
    }
}
