import XCTest

@MainActor
final class CalculatorUITests: XCTestCase {
    func testCalculationValidationAndNewGift() {
        let app = XCUIApplication()
        app.launchArguments = ["-AppleLanguages", "(ru)", "-AppleLocale", "ru_RU"]
        app.launch()
        let calculate = app.buttons["calculate"]
        XCTAssertTrue(calculate.waitForExistence(timeout: 10))
        XCTAssertFalse(calculate.isEnabled)
        attachScreenshot("iOS-input-empty", app: app)

        fill("height", with: "20", in: app)
        fill("width", with: "10", in: app)
        fill("depth", with: "5", in: app)
        XCTAssertTrue(calculate.isEnabled)
        app.buttons["Готово"].tap()
        attachScreenshot("iOS-input-filled", app: app)
        calculate.tap()

        XCTAssertTrue(app.staticTexts["paperHeight"].waitForExistence(timeout: 5))
        XCTAssertEqual(app.staticTexts["paperHeight"].label, "26,7")
        XCTAssertEqual(app.staticTexts["paperWidth"].label, "33,0")
        attachScreenshot("iOS-result", app: app)
        app.buttons["newGift"].tap()
        XCTAssertTrue(calculate.waitForExistence(timeout: 5))
        XCTAssertFalse(calculate.isEnabled)
        XCTAssertEqual(app.textFields["height"].value as? String, "0")

        fill("height", with: "20,5", in: app)
        fill("width", with: "10,5", in: app)
        fill("depth", with: "5", in: app)
        app.buttons["Готово"].tap()
        XCTAssertTrue(calculate.isEnabled)
        calculate.tap()
        XCTAssertTrue(app.staticTexts["paperWidth"].waitForExistence(timeout: 5))
        XCTAssertEqual(app.staticTexts["paperWidth"].label, "34,0")
        XCTAssertEqual(app.staticTexts["paperHeight"].label, "27,2")
    }

    private func fill(_ id: String, with value: String, in app: XCUIApplication) {
        let field = app.textFields[id]
        field.tap()
        field.typeText(value)
    }

    private func attachScreenshot(_ name: String, app: XCUIApplication) {
        let attachment = XCTAttachment(screenshot: app.screenshot())
        attachment.name = name
        attachment.lifetime = .keepAlways
        add(attachment)
    }
}
