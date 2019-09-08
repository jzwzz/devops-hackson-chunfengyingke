import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './activity-info.reducer';
import { IActivityInfo } from 'app/shared/model/activity-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IActivityInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ActivityInfoDetail extends React.Component<IActivityInfoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { activityInfoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="chunfengApp.activityInfo.detail.title">ActivityInfo</Translate> [<b>{activityInfoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="activityName">
                <Translate contentKey="chunfengApp.activityInfo.activityName">Activity Name</Translate>
              </span>
            </dt>
            <dd>{activityInfoEntity.activityName}</dd>
            <dt>
              <span id="user">
                <Translate contentKey="chunfengApp.activityInfo.user">User</Translate>
              </span>
            </dt>
            <dd>{activityInfoEntity.user}</dd>
            <dt>
              <span id="telephone">
                <Translate contentKey="chunfengApp.activityInfo.telephone">Telephone</Translate>
              </span>
            </dt>
            <dd>{activityInfoEntity.telephone}</dd>
            <dt>
              <span id="payStatus">
                <Translate contentKey="chunfengApp.activityInfo.payStatus">Pay Status</Translate>
              </span>
            </dt>
            <dd>{activityInfoEntity.payStatus}</dd>
            <dt>
              <span id="addDate">
                <Translate contentKey="chunfengApp.activityInfo.addDate">Add Date</Translate>
              </span>
            </dt>
            <dd>{activityInfoEntity.addDate}</dd>
          </dl>
          <Button tag={Link} to="/entity/activity-info" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/activity-info/${activityInfoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ activityInfo }: IRootState) => ({
  activityInfoEntity: activityInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActivityInfoDetail);
