package com.limo.notecompanion.util;

import java.util.Locale;

public class Triangle {
    public boolean calcSuccessful;
    public Double alpha, beta, gamma, a, b, c;
    public String alphaTeX, betaTeX, gammaTeX, aTeX, bTeX, cTeX;
    private String lastTex;

    public Triangle(Double alpha, Double beta, Double gamma, Double a, Double b, Double c) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.a = a;
        this.b = b;
        this.c = c;

        if(alpha != null)
            alphaTeX = "\\alpha = " + alpha;
        if(beta != null)
            betaTeX = "\\beta = " + beta;
        if(gamma != null)
            gammaTeX = "\\gamma = " + gamma;
        if(a != null)
            aTeX = "\\a = " + a;
        if(b != null)
            bTeX = "\\b = " + b;
        if(c != null)
            cTeX = "\\c = " + c;

        if(a != null && b != null && c != null && alpha != null && beta != null && gamma != null)
            return;
        calcSuccessful = calc();
    }

    private boolean calc() {
        // 180° Law
        if(alpha == null && beta != null && gamma != null) {
            alpha = 180 - beta - gamma;
            alphaTeX = String.format("\\alpha = 180° - \\beta - \\gamma = 180° - %s° - %s° = %s°", beta, gamma, alpha);
            return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(alpha != null && beta == null && gamma != null) {
            beta = 180 - alpha - gamma;
            betaTeX = String.format("\\beta = 180° - \\alpha - \\gamma = 180° - %s° - %s° = %s°", alpha, gamma, beta);
            return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(alpha != null && beta != null && gamma == null){
            gamma = 180 - alpha - beta;
            gammaTeX = String.format("\\gamma = 180° - \\alpha - \\beta = 180° - %s° - %s° = %s°", alpha, beta, gamma);
            return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }

        // Default Laws Angles
        if(alpha != null && alpha == 90) {
            if(a != null) {
                if(b != null) {
                    if(beta == null) {
                        beta = Math.asin(b / a);
                        betaTeX = String.format("\\beta = \\sin^{-1} \\frac{b}{a} = \\sin^{-1} \\frac{%s}{%s} = %s°", b, a, beta);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
                if(c != null) {
                    if(beta == null) {
                        beta = Math.acos(c / a);
                        betaTeX = String.format("\\beta = \\cos^{-1} \\frac{c}{a} = \\cos^{-1} \\frac{%s}{%s} = %s°", c, a, beta);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            } else {
                if(b != null && c != null) {
                    if(beta == null) {
                        beta = Math.atan(b / c);
                        betaTeX = String.format("\\beta = \\tan^{-1} \\frac{b}{c} = \\tan^{-1} \\frac{%s}{%s} = %s°", b, c, beta);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            }
        }
        if(beta != null && beta == 90) {
            if(b != null) {
                if(c != null) {
                    if(gamma == null) {
                        gamma = Math.asin(c / b);
                        gammaTeX = String.format("\\gamma = \\sin^{-1} \\frac{c}{b} = \\sin^{-1} \\frac{%s}{%s} = %s°", c, b, gamma);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
                if(a != null) {
                    if(gamma == null) {
                        gamma = Math.acos(a / b);
                        gammaTeX = String.format("\\gamma = \\cos^{-1} \\frac{a}{b} = \\cos^{-1} \\frac{%s}{%s} = %s°", a, b, gamma);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            } else {
                if(c != null && a != null) {
                    if(gamma == null) {
                        gamma = Math.atan(c / a);
                        gammaTeX = String.format("\\gamma = \\tan^{-1} \\frac{c}{a} = \\tan^{-1} \\frac{%s}{%s} = %s°", c, a, gamma);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            }
        }
        if(gamma != null && gamma == 90) {
            if(c != null) {
                if(a != null) {
                    if(alpha == null) {
                        alpha = Math.asin(a / c);
                        alphaTeX = String.format("\\alpha = \\sin^{-1} \\frac{a}{c} = \\sin^{-1} \\frac{%s}{%s} = %s°", a, c, alpha);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
                if(b != null) {
                    if(alpha == null) {
                        alpha = Math.acos(b / c);
                        alphaTeX = String.format("\\alpha = \\cos^{-1} \\frac{b}{c} = \\cos^{-1} \\frac{%s}{%s} = %s°", b, c, alpha);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            } else {
                if(a != null && b != null) {
                    if(alpha == null) {
                        alpha = Math.atan(a / b);
                        alphaTeX = String.format("\\alpha = \\tan^{-1} \\frac{a}{b} = \\tan^{-1} \\frac{%s}{%s} = %s°", a, b, alpha);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            }
        }

        // Sine Law Angles
        if(alpha == null && gamma != null && a != null && c != null) {
            alpha = calcSineLawAngle(c, a, gamma);
            alphaTeX = "\\alpha = " + lastTex;
            return a != null && b != null && c != null && beta != null && gamma != null || calc();
        }
        if(alpha == null && beta != null && a != null && b != null) {
            alpha = calcSineLawAngle(b, a, beta);
            alphaTeX = "\\alpha = " + lastTex;
            return a != null && b != null && c != null && beta != null && gamma != null || calc();
        }
        if(beta == null && alpha != null && b != null && a != null) {
            beta = calcSineLawAngle(a, b, alpha);
            betaTeX = "\\beta = " + lastTex;
            return a != null && b != null && c != null && alpha != null && gamma != null || calc();
        }
        if(beta == null && gamma != null && b != null && c != null) {
            beta = calcSineLawAngle(c, b, gamma);
            betaTeX = "\\beta = " + lastTex;
            return a != null && b != null && c != null && alpha != null && gamma != null || calc();
        }
        if(gamma == null && alpha != null && c != null && a != null) {
            gamma = calcSineLawAngle(a, c, alpha);
            gammaTeX = "\\gamma = " + lastTex;
            return a != null && b != null && c != null && alpha != null && beta != null || calc();
        }
        if(gamma == null && beta != null && c != null && b != null) {
            gamma = calcSineLawAngle(b, c, beta);
            gammaTeX = "\\gamma = " + lastTex;
            return a != null && b != null && c != null && alpha != null && beta != null || calc();
        }


        // Cosine Law Angles
        if(alpha == null && a != null && b != null && c != null) {
            alpha = calcCosineLawAngle(b, c, a);
            alphaTeX = "\\alpha = " + lastTex;
            return a != null && b != null && c != null && beta != null && gamma != null || calc();
        }

        // Default Laws Sides
        if(alpha != null && alpha == 90) {
            if(a != null) {
                if(beta != null) {
                    if(b == null) {
                        b = a * Math.sin(beta);
                        bTeX = String.format("b = \\sin \\beta \\cdot a = \\sin %s° \\cdot %s = %s°", beta, a, b);
                        return a != null && b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
                if(gamma != null) {
                    if(c == null) {
                        c = a * Math.cos(gamma);
                        cTeX = String.format("b = \\cos \\gamma \\cdot a = \\cos %s° \\cdot %s = %s°", gamma, a, c);
                        return a != null && b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            } else {
                if(c != null && beta != null) {
                    if(b == null) {
                        b = c * Math.tan(beta);
                        bTeX = String.format("b = \\tan \\beta \\cdot c = \\tan %s° \\cdot %s = %s°", b, c, beta);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            }
        }
        if(beta != null && beta == 90) {
            if(b != null) {
                if(gamma != null) {
                    if(c == null) {
                        c = b * Math.sin(gamma);
                        cTeX = String.format("c = \\sin \\gamma \\cdot b = \\sin %s° \\cdot %s = %s°", alpha, b, c);
                        return a != null && b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
                if(alpha != null) {
                    if(a == null) {
                        a = b * Math.cos(alpha);
                        aTeX = String.format("a = \\cos \\alpha \\cdot b = \\cos %s° \\cdot %s = %s°", alpha, b, a);
                        return a != null && b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            } else {
                if(a != null && gamma != null) {
                    if(c == null) {
                        c = a * Math.tan(gamma);
                        cTeX = String.format("c = \\tan \\gamma \\cdot a = \\tan %s° \\cdot %s = %s°", c, a, gamma);
                        return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
                    }
                }
            }
        }

        // Sine Law Sides
        if(a == null && c != null && alpha != null && gamma != null) {
            a = calcSineLawSide(c, gamma, alpha);
            aTeX = "a = " + lastTex;
            return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(a == null && b != null && alpha != null && beta != null) {
            a = calcSineLawSide(b, beta, alpha);
            aTeX = "a = " + lastTex;
            return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(b == null && a != null && beta != null && alpha != null) {
            b = calcSineLawSide(a, alpha, beta);
            bTeX = "b = " + lastTex;
            return a != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(c == null && b != null && gamma != null && beta != null) {
            c = calcSineLawSide(b, beta, gamma);
            cTeX = "c = " + lastTex;
            return a != null && b != null && alpha != null && beta != null && gamma != null || calc();
        }

        // Cosine Law Sides
        if(a == null && b != null && c != null && alpha != null) {
            a = calcCosineLawSide(b, c, alpha);
            aTeX = "a = " + lastTex;
            return b != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(b == null && a != null && c != null && beta != null) {
            b = calcCosineLawSide(a, c, beta);
            bTeX = "b = " + lastTex;
            return a != null && c != null && alpha != null && beta != null && gamma != null || calc();
        }
        if(c == null && a != null && b != null && gamma != null) {
            c = calcCosineLawSide(a, b, gamma);
            cTeX = "c = " + lastTex;
            return a != null && b != null && alpha != null && beta != null && gamma != null || calc();
        }

        return a != null && b != null && c != null && alpha != null && beta != null && gamma != null;
    }

    // get c from a, b and gamma
    private double calcCosineLawSide(double a, double b, double gamma) {
        double c = Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)) - (2 * a * b * Math.cos(gamma)));
        lastTex = String.format(Locale.ENGLISH, "\\sqrt{ %s^2 + %s^2 - 2 \\cdot %s \\cdot %s \\cdot \\cos %s° } = %s", a, b, a, b, gamma, c);
        return c;
    }

    // get b from a, alpha and beta
    private double calcSineLawSide(double a, double alpha, double beta) {
        double b = beta / alpha * a;
        lastTex = String.format(Locale.ENGLISH, "\\frac{%s°}{%s°} \\cdot %s = %s°", beta, alpha, a, b);
        return b;
    }

    // get gamma from a, b and c
    private double calcCosineLawAngle(double a, double b, double c) {
        double gamma = Math.acos((Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2)) / (2 * a * b));
        lastTex = String.format(Locale.ENGLISH, "\\cos^{-1}(\\frac{%s^2+%s^2-%s^2}{2 \\cdot %s \\cdot %s}) = %s", a, b, c, a, b, gamma);
        return gamma;
    }

    // get beta from a, b and alpha
    private double calcSineLawAngle(double a, double b, double alpha) {
        double beta = b / a * alpha;
        lastTex = String.format(Locale.ENGLISH, "\\frac{%s}{%s} \\cdot %s°", b, a, alpha);
        return beta;
    }
}
