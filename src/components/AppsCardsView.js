import AppCard from "./AppCard";

function AppsCardsView(props) {
    return (
        <div>
            <div className="apps_card_wrapper">
                {
                    app_arr.map (item => (
                        <AppCard name={item.name}></AppCard>
                    ))
                }
            </div>

        </div>
    )
}

export default AppsCardsView;