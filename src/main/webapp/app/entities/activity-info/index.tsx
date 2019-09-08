import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ActivityInfo from './activity-info';
import ActivityInfoDetail from './activity-info-detail';
import ActivityInfoUpdate from './activity-info-update';
import ActivityInfoDeleteDialog from './activity-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActivityInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActivityInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActivityInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ActivityInfo} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ActivityInfoDeleteDialog} />
  </>
);

export default Routes;
