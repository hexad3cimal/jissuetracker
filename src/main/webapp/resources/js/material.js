/**
 * Created by jovin on 2/6/16.
 */
!function (e, t) {
    "use strict";
    function a(e, t,type) {
        var a = type.toString();
        $("#bouncer").toaster({styleclass: e, bounce: t, message: a})
    }

    function i(e) {
        function t(e) {
            return e.replace(/</g, "&lt;").replace(/>/g, "&gt;")
        }

        $(e).each(function (e, a) {
            var i = $(a), s = t(i.html());
            i.html(s)
        }), prettyPrint()
    }

    t["true"] = e, function (e) {
        function t(e) {
            var t = e.split(/\n/);
            t.shift(), t.splice(-1, 1);
            var a = t[0].length - t[0].trim().length, i = new RegExp(" {" + a + "}");
            return t = t.map(function (e) {
                return e.match(i) && (e = e.substring(a)), e
            }), t = t.join("\n")
        }

        window.prettyPrintInit = i, window.addToast = a, e("a.page-scroll").on("click", function (t) {
            t.preventDefault();
            var a = e(this);
            e("html, body").stop().animate({scrollTop: e(a.attr("href")).offset().top}, 1e3, "easeInOutExpo")
        }), e("body").delegate('a[href="#"]', "click", function (e) {
            e.preventDefault()
        }), e("body").delegate(".bmd-component", "mouseenter mouseleave", function (a) {
            //var i = e(this).children("#source-button");
            //if ("mouseenter" === a.type)if (0 === i.length) {
            //    var s = e("<div id='source-button' class='btn bmd-bg-purple bmd-text-purple-50 btn-xs'>&lt; &gt;</div>").on("click", function () {
            //        var a = e(this).parent().find(".bmd-ink").remove();
            //        a = e(this).parent().html(), a = t(a), e("#source-modal pre").text(a), e("#source-modal").modal()
            //    });
            //    e(this).append(s), s.show()
            //} else i.show(); else i.hide()
        }), e(document).ready(function () {
            i(".prettyprint"), setTimeout(function () {
                e("#menu").removeClass("bmd-sidebar-active")
            }, 2e3), "undefined" != typeof DEBUG && e.ajaxSetup({cache: !1}), e("#brand").on("click", function () {
                e("#component").fadeOut("slow"), e("#home").fadeIn("slow")
            }), e("a[data-component]").on("click", function (t) {
                t.preventDefault();
                var a = e(this).data("component"), i = e("#component");
                i.find("#" + a).length ? (e("#home").fadeOut("slow"), i.fadeIn("slow", function () {
                    window.scrollTo(0, 0)
                })) : e("#component").load(a + ".html", function () {
                    "undefined" == typeof DEBUG && (ga("set", {
                        page: "/bmd/" + a + ".html",
                        title: "bmd-" + a
                    }), ga("send", "pageview")), e("#home").fadeOut("slow"), e(this).children("*:first-child").attr("id", a), e(this).fadeIn("slow", function () {
                        window.scrollTo(0, 0)
                    })
                })
            })
        })
    }(jQuery);
    var s = {
        popoverStateTemplate: '<div class="popover bmd-floating bmd-popover-state" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
        tooltipStateTemplate: '<div class="tooltip bmd-tooltip-state" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner bmd-floating"></div></div>'
    }, n = {
        ripple: function (e) {
            var t, a, i, s = ["checkbox", "radio"];
            $("body").delegate(e, "click", function (e) {
                var n, o = $(this);
                if (!(o.closest(".disabled").length > 0 || o.siblings("input[disabled]").length > 0)) {
                    if (-1 !== s.indexOf(o.attr("type")))var d = o.next(".bmd-ink"); else var d = o.find(".bmd-ink");
                    d.length ? (n = d.next("span"), d.removeClass("bmd-animate")) : -1 !== s.indexOf(o.attr("type")) ? (n = $(this).next("span"), $("<span class='bmd-ink'></span>").insertAfter(o), d = o.next(".bmd-ink")) : (o.prepend("<span class='bmd-ink'></span>"), d = o.find(".bmd-ink")), d.height() || d.width() || (-1 !== s.indexOf(o.attr("type")) ? (t = Math.max(n.outerWidth(), n.outerHeight()), d.css({
                        height: t,
                        width: t,
                        position: "fixed"
                    })) : (t = o.hasClass("bmd-switch-thumb") ? o.outerHeight() : Math.max(o.outerWidth(), o.outerHeight()), d.css({
                        height: t,
                        width: t,
                        position: "absolute"
                    }))), -1 !== s.indexOf(o.attr("type")) ? (a = n.offset().left, i = n.offset().top - window.pageYOffset) : o.hasClass("bmd-switch-thumb") ? (a = d.height() / -2, i = 0) : (a = e.pageX - o.offset().left - d.width() / 2, i = e.pageY - o.offset().top - d.height() / 2), d.css({
                        top: i + "px",
                        left: a + "px"
                    }).addClass("bmd-animate").one("webkitAnimationEnd animationend", function (e) {
                        e.stopPropagation(), $(this).removeClass("bmd-animate").trigger("hidden.bmd.ink")
                    }), $(window).on("resize", function () {
                        $(".bmd-ink").remove()
                    })
                }
            })
        }, popover: function (e) {
            $(e).each(function (e, t) {
                var a = $(t), i = a.data("bmd-state");
                a.popover({template: s.popoverStateTemplate.replace("bmd-popover-state", "bmd-popover-" + i)})
            })
        }, tooltip: function (e) {
            $(e).each(function (e, t) {
                var a = $(t), i = a.data("bmd-state");
                a.tooltip({template: s.tooltipStateTemplate.replace("bmd-tooltip-state", "bmd-tooltip-" + i)})
            })
        }, sidebar: function (e) {
            var t, a, i = [];
            e || (e = ""), t = $(e + " .bmd-page-container"), t.each(function (e, t) {
                var a = $(t);
                i.push(parseFloat(a.css("margin-left"))), a.hasClass("bmd-allow-overflow") && a.width(a.parent().outerWidth() - i[e])
            }), t.hasClass("bmd-sidebar-fix") && t.on("transitionend webkitTransitionEnd", function () {
                $(this).hasClass("bmd-sidebar-active") && $(this).closest(".bmd-sidebar").css("z-index", 999)
            }), a = $(e + " .bmd-sidebar-toggle"), a.click(function (e) {
                e.preventDefault();
                var t = $($(this).data("target"));
                t && t.hasClass("bmd-sidebar") && (t.toggleClass("bmd-sidebar-active"), !t.hasClass("bmd-sidebar-active") && t.hasClass("bmd-sidebar-fix") && t.css("z-index", -1))
            }), t.hasClass("bmd-allow-overflow") && $(window).on("resize", function () {
                t.each(function (e, t) {
                    var a = $(t);
                    a.hasClass("bmd-allow-overflow") && a.width(a.parent().outerWidth() - i[e])
                })
            })
        }, select: function (e) {
            $("body").delegate(e, "click", function (e) {
                e.preventDefault();
                var t = $(this), a = t.data("value") || t.text();
                t.closest(".dropdown-menu").prev(".dropdown-toggle").children(".bmd-selected-value").text(a)
                t.closest(".dropdown-menu").prev(".dropdown-toggle").children(".advocate-gender").text(a)
                t.closest(".dropdown-menu").prev(".dropdown-toggle").children(".user-gender").text(a)
            })
        }, bottomSheet: function () {
            $("body").delegate(".bmd-bottom-sheet [data-dismiss]", "click", function (e) {
                e.preventDefault();
                var t = $(this).data("dismiss");
                t && $(t).removeClass("active")
            }), $("body").delegate('[data-toggle="bmd-bottom-sheet"]', "click", function (e) {
                e.preventDefault();
                var t = $(this).data("target");
                t && $(t).toggleClass("active")
            })
        }, fabSpeedDial: function () {
            $("body").delegate(".bmd-fab-speed-dialer:not(.bmd-morph-fab)", "click", function (e) {
                e.preventDefault(), $(this).toggleClass("press")
            }), $("body").delegate('.bmd-fab-speed-dial-list .bmd-fab[data-dismiss="true"]', "click", function (e) {
                e.preventDefault(), $(this).closest(".bmd-fab-speed-dial-container").children(".bmd-fab-speed-dialer").removeClass("press")
            })
        }, treeview: function () {
            var e = ($(".bmd-treeview"), $(".bmd-treeview > .bmd-treeview-root")), t = function (e) {
                if (0 !== e.length) {
                    var a, i = e.data("expanded-icon"), s = e.data("collapsed-icon"), n = e.data("item-icon");
                    if (i && s) {
                        var o = '<span class="bmd-treeview-collapsed ' + s + '"></span> <span class="bmd-treeview-expanded ' + i + '"></span> ';
                        e.find("a[data-toggle]:not(:has(.bmd-treeview-collapsed)):not(:has(.bmd-treeview-expanded))").prepend(o)
                    }
                    n && (a = e.find("ul > li > a:not([data-toggle]):not(:has(.bmd-treeview-icon))"), n = '<span class="bmd-treeview-icon ' + n + '"></span> ', a.prepend(n)), e.hasClass("bmd-treeview-root") || t(e.parent("ul").parent(".bmd-treeview-branch, .bmd-treeview-root"))
                }
            };
            e.each(function (e, a) {
                var i = $(a).find(".bmd-treeview-branch:last");
                t(i)
            })
        }, inputFile: function (e) {
            var t = $(e);
            t.each(function (e, t) {
                var a = $(t), i = a.is("[multiple]") ? " multiple" : "", s = a.siblings("label");
                $('<input type="file" class="bmd-input"' + i + ">").insertAfter(s).on("change", function () {
                    var e = "";
                    $.each(this.files, function (t, a) {
                        e += a.name + " "
                    }), a.val(e)
                })
            })
        }
    };
    !function (e) {
        e(document).ready(function () {
            n.popover('[data-toggle="popover"][data-bmd-state]'), n.tooltip('[data-toggle="tooltip"][data-bmd-state]'), n.sidebar(), n.ripple(".bmd-ripple, .bmd-ripple-only"), n.select(".bmd-select .dropdown-menu>li>a"), n.bottomSheet(), n.fabSpeedDial(), n.treeview(), n.inputFile(".bmd-input-file")
        }), window.bmd_GLOBAL = s, window.bmd_INITIAL = n
    }(jQuery), function (e) {
        function t(t) {
            return this.each(function () {
                var i = e(this), s = i.data("bmd.toaster"), n = e.extend({}, a.DEFAULTS, i.data(), "object" == typeof t && t);
                s ? "hide" === t ? s.hide() : s.newToast(t) : i.data("bmd.toaster", s = new a(this, n))
            })
        }

        var a = function (t, i) {
            this.options = e.extend({}, a.DEFAULTS, i), this.$element = e(t)
        };
        a.DEFAULTS = {
            bounce: "up",
            message: null,
            styleclass: "default",
            hide: 4,
            hidebutton: !1
        }, a.prototype.newToast = function (t) {
            var a = this;
            if (t = "string" == typeof t ? {message: t} : t, t = e.extend({}, this.options, t), null == t.message)return this.$element;
            t.hide && "number" != typeof t.hide && (t.hide = "manual");
            var i = (new Date).valueOf(), s = '<li id="' + i + '" class="hide bmd-floating ' + t.styleclass + '">' + (t.hidebutton ? '<button type="button" class="close btn-link" aria-hidden="true">×</button>' : "") + '<p class="bmd-toast-message">' + t.message + "</p></li>", n = e(s).appendTo(this.$element);
            n.children(".close").on("click", e.proxy(this.hide, this, i));
            var o = {relatedTarget: n}, d = e.Event("show.bmd.toast", o);
            return n.one("webkitAnimationEnd animationend", function (s) {
                var n = e(this);
                a.$element.trigger(s = e.Event("shown.bmd.toast", {relatedTarget: n})), "manual" != t.hide && setTimeout(e.proxy(a.hide, a, i), 1e3 * t.hide)
            }), this.$element.trigger(d).children("li:last").removeClass("hide").addClass("bmd-toast-bounce-" + t.bounce), this.$element
        }, a.prototype.hide = function (t) {
            var a = this, i = this.$element.children(t ? "#" + t : "li:first");
            return 0 === i.length ? this.$element : (this.$element.trigger(e.Event("hide.bmd.toast", {relatedTarget: i})), i.fadeOut("slow", function (t) {
                a.$element.trigger(t = e.Event("hidden.bmd.toast", {relatedTarget: e(this)})), e(this).remove()
            }), this.$element)
        };
        var i = e.fn.toaster;
        e.fn.toaster = t, e.fn.toaster.Constructor = a, e.fn.toaster.noConflict = function () {
            return e.fn.toaster = i, this
        }, e(window).on("load.bmd.toaster.data-api", function () {
            e("ul.bmd-toaster").each(function () {
                var a = e(this);
                t.call(a, a.data())
            })
        })
    }(jQuery), function (e) {
        function t(t) {
            return this.each(function () {
                var i = e(this), s = i.data("bmd.morphFab"), n = e.extend({}, a.DEFAULTS, i.data(), "object" == typeof t && t);
                s || i.data("bmd.morphFab", s = new a(this, n)), "expand" == t ? (s.morphExpand(), s.morphFabMove()) : "close" == t && s.morphClose()
            })
        }

        var a = function (t, a) {
            if (this.options = a, this.$element = e(t), this.$target = e(this.$element.data("target")), this.$fabParent = this.$element.parent().hasClass(".bmd-fab-speed-dial-container") || "LI" == this.$element.parent()[0].nodeName ? this.$element.parent() : this.$element, !this.$target.length)return this.$fakeFab = null, void console.log('Morph FAB has no target="' + this.$element.data("target") + '", initialization stopped.');
            var i = this.$target.find(".bmd-morph-fab-fake");
            this.$fakeFab = i.length > 0 ? i : this.$element.clone().removeAttr("style").addClass("bmd-morph-fab-fake").appendTo(this.$target), this.resizeTarget(), this.$target.hasClass("bmd-morph-closed") ? this.$fakeFab.addClass("hidden") : this.$fabParent.addClass("hidden"), this.eventSetup()
        };
        a.DEFAULTS = {stickfab: !1, sticktarget: !1, closemenu: !1}, a.prototype.toggleMenu = function (e) {
            var t = this;
            "open" == e ? (this.$element.closest(".bmd-sidebar").addClass("bmd-sidebar-active"), this.$element.closest(".bmd-bottom-sheet").addClass("active"), this.$element.closest(".bmd-fab-speed-dial-container").children(".bmd-fab-speed-dialer").addClass("press")) : setTimeout(function () {
                t.$element.closest(".bmd-sidebar").removeClass("bmd-sidebar-active"), t.$element.closest(".bmd-bottom-sheet").removeClass("active"), t.$element.closest(".bmd-fab-speed-dial-container").children(".bmd-fab-speed-dialer").removeClass("press")
            }, 300)
        }, a.prototype.fabExpand = function () {
            this.$fakeFab.one("webkitAnimationEnd animationend", e.proxy(this.morphPostHandler, this)), this.$target.addClass("morphing")
        }, a.prototype.morphPostHandler = function () {
            this.$target.hasClass("morphing") ? (this.$fakeFab.addClass("hidden"), this.$target.removeClass("morphing expanding bmd-morph-closed").addClass("bmd-morph-expanded"), this.$element.trigger(e.Event("expanded.bmd.morphFab", {relatedTarget: this.$target}))) : this.morphFabMove()
        }, a.prototype.morphFabArrived = function () {
            this.$target.hasClass("closing") ? (this.$target.removeClass("closing").addClass("bmd-morph-closed"), this.resizeTarget(), this.$fabParent.removeClass("invisible"), this.options.closemenu && this.toggleMenu("close"), this.$fakeFab.addClass("hidden").removeClass("moving"), this.$element.trigger(e.Event("closed.bmd.morphFab", {relatedTarget: this.$target}))) : this.$fakeFab.hasClass("moving") && (this.$fabParent.addClass("hidden").removeClass("invisible"), this.resetFakeStyle(), this.$fakeFab.removeClass("moving"), this.options.closemenu && this.toggleMenu("close"), setTimeout(e.proxy(this.fabExpand, this), 100))
        }, a.prototype.resetFakeStyle = function () {
            this.$fakeFab.css({transition: "", position: "", right: "", bottom: "", margin: "", top: "", left: ""})
        }, a.prototype.morphFabMove = function () {
            if (!this.$fakeFab.hasClass("moving") && (this.$target.hasClass("expanding") || this.$target.hasClass("closing"))) {
                var t = this, a = {}, i = function () {
                    if (t.$target.hasClass("expanding")) {
                        var i = t.$element.offset();
                        t.resetFakeStyle(), a = t.$fakeFab.offset()
                    } else var i = t.$fakeFab.offset();
                    t.$fakeFab.css({
                        transition: "none",
                        position: "fixed",
                        right: "",
                        bottom: "",
                        margin: 0,
                        top: i.top - window.pageYOffset,
                        left: i.left - window.pageXOffset
                    }), t.$fakeFab.one("webkitTransitionEnd transitionend", e.proxy(t.morphFabArrived, t))
                };
                this.$fakeFab.addClass("moving"), this.$target.hasClass("expanding") ? this.targetResized.done(function () {
                    t.$fakeFab.addClass("invisible").removeClass("hidden"), i(), t.$fakeFab.removeClass("invisible"), t.$fabParent.addClass("invisible"), setTimeout(function () {
                        t.$fakeFab.css({
                            transition: "all 0.2s ease-in",
                            top: a.top - window.pageYOffset,
                            left: a.left - window.window.pageXOffset
                        })
                    }, 100)
                }) : (this.$fabParent.addClass("invisible").removeClass("hidden"), this.options.stickfab || this.$fabParent.appendTo(this.$element.closest("ul")), i(), setTimeout(function () {
                    var e = t.$fabParent.parent(".bmd-fab-speed-dial-list").prev(".bmd-fab-speed-dialer"), a = {
                        top: 0,
                        left: 0
                    };
                    e.length && !e.hasClass("press") && (a = {top: e.offset().top, left: e.offset().left});
                    var i = {
                        top: (a.top || t.$element.offset().top) - window.pageYOffset,
                        left: (a.left || t.$element.offset().left) - window.pageXOffset
                    };
                    t.$fakeFab.css({transition: "all 0.2s ease-in", top: i.top, left: i.left})
                }, 100))
            }
        }, a.prototype.morphClose = function () {
            this.$target.hasClass("bmd-morph-closed") || (this.$fakeFab.one("webkitAnimationEnd animationend", e.proxy(this.morphPostHandler, this)), this.$fakeFab.removeClass("hidden"), this.options.closemenu && this.toggleMenu("open"), this.$target.removeClass("bmd-morph-expanded").addClass("closing"))
        }, a.prototype.eventSetup = function () {
            var t = this;
            this.$target.find(".bmd-morph-close").on("click", function (a) {
                a.preventDefault(), e(this).hasClass("bmd-ripple") || t.morphClose()
            }).delegate(".bmd-ink", "hidden.bmd.ink", function () {
                t.morphClose()
            }), this.$element.on("click", function (e) {
                e.preventDefault(), t.morphExpand(), t.$element.hasClass("bmd-ripple") || t.morphFabMove()
            }).delegate(".bmd-ink", "hidden.bmd.ink", e.proxy(t.morphFabMove, t))
        }, a.prototype.morphExpand = function () {
            this.$target.hasClass("bmd-morph-expanded") || (this.$target.addClass("expanding"), this.options.sticktarget || this.$target.parent().appendTo(this.$target.parent().parent()), this.resizeTarget())
        }, a.prototype.resizeTarget = function () {
            if (this.$target.parent().css("transition", "all .35s cubic-bezier(.55,0,.1,1)"), this.$target.hasClass("expanding")) {
                var t = this;
                this.targetResized = e.Deferred(), setTimeout(function () {
                    t.$target.parent().one("webkitTransitionEnd transitionend", function () {
                        t.targetResized.resolve(!0)
                    }), t.$target.parent().css({
                        width: "",
                        padding: "",
                        "min-height": "",
                        "max-height": "",
                        overflow: ""
                    })
                })
            } else this.$target.hasClass("bmd-morph-closed") && this.$target.parent().css({
                width: 0,
                padding: 0,
                "min-height": 0,
                "max-height": 0,
                overflow: "hidden"
            })
        };
        var i = e.fn.morphFab;
        e.fn.morphFab = t, e.fn.morphFab.Constructor = a, e.fn.morphFab.noConflict = function () {
            return e.fn.morphFab = i, this
        }, e(window).on("load.bmd.morphFab.data-api", function () {
            e(".bmd-morph-fab:not(.bmd-morph-fab-fake)").each(function () {
                var a = e(this);
                t.call(a, a.data())
            })
        })
    }(jQuery)
}({}, function () {
    return this
}());