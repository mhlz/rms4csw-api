package st.ilu.rms4csw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mischa Holz
 */
public class NetworkUtil {

    private static final Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

    public static boolean isLocalNetworkRequest() {
        if("true".equals(System.getenv("RMS_IGNORE_LOCAL_NETWORK"))) {
            logger.info("Ignoring IP address because env");
            return true;
        }

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if(sra == null) {
            logger.info("No request attached to this thread.");
            return false;
        }

        HttpServletRequest request = sra.getRequest();
        return isLocalNetworkRequest(request);
    }

    public static boolean isLocalNetworkRequest(HttpServletRequest request) {
        if("true".equals(System.getenv("RMS_IGNORE_LOCAL_NETWORK"))) {
            logger.info("Ignoring IP address because env");
            return true;
        }

        String realIp = request.getHeader("X-Real-IP");
        logger.info("X-Real-IP: {}", realIp);

        String forwardedFor = request.getHeader("X-Forwarded-For");
        logger.info("X-Forwarded-For: {}", forwardedFor);

        String remoteAddr = request.getRemoteAddr();
        logger.info("RemoteAddr: {}", remoteAddr);


        if(request.getHeader("X-Real-IP") != null) {
            return realIp.startsWith("192.168") || realIp.startsWith("127.0.0.1");
        }

        if(request.getHeader("X-Forwarded-For") != null) {
            return forwardedFor.startsWith("192.168") || forwardedFor.startsWith("127.0.0.1");
        }

        return remoteAddr.startsWith("192.168") || remoteAddr.startsWith("127.0.0.1");
    }

}
