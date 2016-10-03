package com.easybuy.control.servlet;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.gimpy.DropShadowGimpyRenderer;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;
import cn.apiclub.captcha.text.producer.TextProducer;
import cn.apiclub.captcha.text.renderer.ColoredEdgesWordRenderer;

import com.easybuy.control.Constants;

/**
 * A servlet generates a captcha & save its answer to session for later use.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 3, 2016
 *
 */
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static int CAPTCHA_WIDTH = 70;
    private static int CAPTCHA_HEIGHT = 26;
    private static int CAPTCHA_LENGTH = 4;
    private static int CAPTCHA_FONT_SIZE = 22;

    private static final Random random = new Random();
    private static final List<Color> COLORS = new ArrayList<Color>();
    private static final List<Font> FONTS = new ArrayList<Font>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        COLORS.add(Color.RED);
        COLORS.add(Color.BLACK);
        COLORS.add(Color.BLUE);

        FONTS.add(new Font("Geneva", Font.PLAIN, CAPTCHA_FONT_SIZE));
        FONTS.add(new Font("Courier", Font.BOLD, CAPTCHA_FONT_SIZE));
        FONTS.add(new Font("Arial", Font.BOLD, CAPTCHA_FONT_SIZE));
        FONTS.add(new Font("Tahoma", Font.PLAIN, CAPTCHA_FONT_SIZE));
    }

    class CustomTextProducer implements TextProducer {

        private static final String dict = "ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz3456789";

        int length = 0;


        public CustomTextProducer(int len) {
            this.length = len;
        }


        @Override
        public String getText() {
            int n = dict.length();
            StringBuilder ret = new StringBuilder();
            for (int i = 0; i < length; i++) {
                ret.append(dict.charAt(random.nextInt(n)));
            }
            return ret.toString();
        }

    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ColoredEdgesWordRenderer wordRenderer = new ColoredEdgesWordRenderer(COLORS, FONTS);
        Captcha captcha = new Captcha.Builder(CAPTCHA_WIDTH, CAPTCHA_HEIGHT)
                .addText(new CustomTextProducer(CAPTCHA_LENGTH), wordRenderer)
                .gimp(new DropShadowGimpyRenderer())
                .build();
        req.getSession().setAttribute(Constants.SESS_ATTR_NAME_CAPTCHA_ANSWER, captcha.getAnswer());
        CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }
}
