package vanrrtech.app.retrofitandrx.modelview

import android.opengl.Visibility
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RecycleViewUnitTest : AutoCloseKoinTest(){

    private lateinit var scenario: FragmentScenario<FragmentHost>

    @Before
    fun setUp() {
        startKoin {
            modules(
                // TODO set view model
            )
        }
        scenario.moveToState(newState = Lifecycle.State.STARTED)
    }

    @Test
    fun assert_Actors_List_Fragment_Render_The_UI_According_The_Loading_State() {
        //TODO
        scenario.onFragment { fragment ->
            fragment.listAdapter.ViewHolder(null, null).renderView()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.viewHolder
                , null).renderView()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.mContext, null).renderView()

            // download

            fragment.listAdapter.ViewHolder(null, null).download()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.viewHolder
                , null).download()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.mContext, null).download()
        }


        onView(withId(R.id.title))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download))?.check(matches(withEffectiveVisibility(Visibility.Invisible)))
        onView(withId(R.id.thumb))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.desc))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.rating))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download).getDrawable)?.check(matches(R.drawable.ic.downloading)))
        onView(withId(R.id.download).getDrawable)?.check(matches(R.drawable.ic.download)))

    }

    @Test
    fun assert_Actors_List_Fragment_Render_The_UI_According_The_Empty_Fragment_Host_Data_State() {
        //TODO
        scenario.onFragment { fragment ->
            fragment.listAdapter.ViewHolder(null, null).renderView()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.viewHolder
                , null).renderView()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.mContext, null).renderView()

            // download

            fragment.listAdapter.ViewHolder(null, null).download()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.viewHolder
                , null).download()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.mContext, null).download()
        }

        onView(withId(R.id.title))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download))?.check(matches(withEffectiveVisibility(Visibility.Invisible)))
        onView(withId(R.id.thumb))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.desc))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.rating))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download).getDrawable)?.check(matches(R.drawable.ic.downloading)))
        onView(withId(R.id.download).getDrawable)?.check(matches(R.drawable.ic.download)))

    }

    @Test
    fun assert_Actors_List_Fragment_Render_The_UI_According_The_Fragment_Host_Data_State() {
        //TODO
        scenario.onFragment { fragment ->
            fragment.listAdapter.ViewHolder(null, null).renderView()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.viewHolder
                , null).renderView()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.mContext, null).renderView()

            // download

            fragment.listAdapter.ViewHolder(null, null).download()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.viewHolder
                , null).download()
            fragment.listAdapter.ViewHolder(fragment.listAdapter.mContext, null).download()
        }


        onView(withId(R.id.title))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download))?.check(matches(withEffectiveVisibility(Visibility.Invisible)))
        onView(withId(R.id.thumb))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.desc))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.rating))?.check(matches(withEffectiveVisibility(Visibility.Visible)))
        onView(withId(R.id.download).getDrawable)?.check(matches(R.drawable.ic.downloading)))
        onView(withId(R.id.download).getDrawable)?.check(matches(R.drawable.ic.download)))

    }
}