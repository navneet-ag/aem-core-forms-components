/*
 *  Copyright 2024 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
describe("Form Runtime with hCaptcha Input", () => {

    const FT_HCAPTCHA = "FT_FORMS-12407";
    const pagePath = "content/forms/af/core-components-it/samples/hcaptcha/basic.html"
    const bemBlock = 'cmp-adaptiveform-hcaptcha'
    const IS = "adaptiveFormHCaptcha"
    const selectors = {
        hcaptcha : `[data-cmp-is="${IS}"]`
    };

    let formContainer = null

    let toggle_array = [];

    before(() => {
        cy.fetchFeatureToggles().then((response) => {
            if (response.status === 200) {
                toggle_array = response.body.enabled;
            }
        });
    });


    // render the form with captcha, we have whitelisted the "Missing required parameters: sitekey" error
    beforeEach(() => {
        cy.previewForm(pagePath).then((p) => {
            formContainer = p;
        });
    });

    const checkHTML = (id, state) => {
        const visible = state.visible;
        const passVisibleCheck = `${visible === true ? "" : "not."}be.visible`;
        const passDisabledAttributeCheck = `${state.enabled === false || state.readOnly === true ? "" : "not."}have.attr`;
        const value = state.value
        cy.get(`#${id}`)
            .should(passVisibleCheck)
            .invoke('attr', 'data-cmp-visible')
            .should('eq', visible.toString());
        cy.get(`#${id}`)
            .invoke('attr', 'data-cmp-enabled')
            .should('eq', state.enabled.toString());
        return cy.get(`#${id}`).within((root) => {
            cy.get('*').should(passVisibleCheck)
        })
    }

    it(" should get model and view initialized properly ", () => {
        if (toggle_array.includes(FT_HCAPTCHA)) {
            expect(formContainer, "formcontainer is initialized").to.not.be.null;
            expect(formContainer._model.items.length, "model and view elements match").to.equal(Object.keys(formContainer._fields).length);
            Object.entries(formContainer._fields).forEach(([id, field]) => {
                expect(field.getId()).to.equal(id)
                expect(formContainer._model.getElement(id), `model and view are in sync`).to.equal(field.getModel())
            });
        }
    })

    it(" model's changes are reflected in the html ", () => {
        if (toggle_array.includes(FT_HCAPTCHA)) {
            const [id, fieldView] = Object.entries(formContainer._fields)[0]
            const model = formContainer._model.getElement(id)
            cy.get('div.h-captcha').should('exist');

            checkHTML(model.id, model.getState()).then(() => {
                model.visible = false
                return checkHTML(model.id, model.getState())
            }).then(() => {
                model.enable = false
                return checkHTML(model.id, model.getState())
            })
        }
    });

    it(" html changes are reflected in model ", () => {
        if (toggle_array.includes(FT_HCAPTCHA)) {
            const [id, fieldView] = Object.entries(formContainer._fields)[0]
            const model = formContainer._model.getElement(id)
            cy.log(model.getState().value)
            cy.get(`#${id}`).click().then(x => {
                cy.log(model.getState().value)
                expect(model.getState().value).to.not.be.null
            })
        }
    });


    it("decoration element should not have same class name", () => {
        if (toggle_array.includes(FT_HCAPTCHA)) {
            expect(formContainer, "formcontainer is initialized").to.not.be.null;
            cy.wrap().then(() => {
                const id = formContainer._model._children[0].id;
                cy.get(`#${id}`).parent().should("not.have.class", bemBlock);
            })
        }
    })

    it("client side validation should fail if captcha is not filled", () => {
        if (toggle_array.includes(FT_HCAPTCHA)) {
            expect(formContainer, "formcontainer is initialized").to.not.be.null;
            cy.get(`.cmp-adaptiveform-button__widget`).click().then(x => {
                cy.get('.cmp-adaptiveform-hcaptcha__errormessage').should('exist').contains('Please fill in this field.');
            });
        }
    })

    it("submission should pass for mandatory captcha", () => {
        if (toggle_array.includes(FT_HCAPTCHA)) {
            expect(formContainer, "formcontainer is initialized").to.not.be.null;
            cy.get(`div.h-captcha iframe`).should('be.visible').then($iframe => {
                cy.wrap($iframe).then($iframe => {
                    cy.window().should('have.property', 'hcaptcha').and('not.be.undefined')
                        .then((hcaptcha) => {
                            hcaptcha.execute();
                            cy.wait(2000);
                            cy.get(`.cmp-adaptiveform-button__widget`).click().then(x => {
                                cy.get('body').should('contain', "Thank you for submitting the form.\n")
                            });
                        })
                });
            });
        }
    })

})
